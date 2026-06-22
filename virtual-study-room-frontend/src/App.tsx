import { useEffect, useState } from 'react';
import './App.css';

interface User {
  id: number;
  name: string;
  email: string;
  dob: string;
}

interface Room {
  id: number;
  name: string;
  ownerId: number;
  ownerName?: string;
}

interface ChatMessage {
  id: string;
  roomId: number;
  userId: number;
  userName: string;
  text: string;
  createdAt: string;
}

const API_BASE = 'http://localhost:8080/api/v1';
const STORAGE_USER = 'vsr-current-user';
const STORAGE_MESSAGES = 'vsr-room-messages';

function App() {
  const [currentUser, setCurrentUser] = useState<User | null>(null);
  const [userName, setUserName] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const [userDob, setUserDob] = useState('');
  const [rooms, setRooms] = useState<Room[]>([]);
  const [roomName, setRoomName] = useState('');
  const [selectedRoom, setSelectedRoom] = useState<Room | null>(null);
  const [messageText, setMessageText] = useState('');
  const [messagesByRoom, setMessagesByRoom] = useState<Record<number, ChatMessage[]>>({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const storedUser = localStorage.getItem(STORAGE_USER);
    if (storedUser) setCurrentUser(JSON.parse(storedUser));
    const storedMessages = localStorage.getItem(STORAGE_MESSAGES);
    if (storedMessages) setMessagesByRoom(JSON.parse(storedMessages));
  }, []);

  useEffect(() => {
    if (currentUser) {
      localStorage.setItem(STORAGE_USER, JSON.stringify(currentUser));
      fetchRooms();
    }
  }, [currentUser]);

  useEffect(() => {
    localStorage.setItem(STORAGE_MESSAGES, JSON.stringify(messagesByRoom));
  }, [messagesByRoom]);

  const fetchRooms = async () => {
    setError(null);

    try {
      const res = await fetch(`${API_BASE}/rooms`);
      if (!res.ok) throw new Error('Failed to fetch rooms');
      const data = await res.json();
      setRooms(data);
    } catch (err) {
      console.error(err);
      setError('Unable to load rooms. Check API availability.');
    }
  };

  const login = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!userName.trim() || !userEmail.trim() || !userDob.trim()) {
      setError('Name, email, and date of birth are required');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const payload = {
        name: userName.trim(),
        email: userEmail.trim(),
        dob: userDob,
      };

      const res = await fetch(`${API_BASE}/users`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        throw new Error('Failed to create user account');
      }

      const savedUser: User = await res.json();
      setCurrentUser(savedUser);
      setUserName('');
      setUserEmail('');
      setUserDob('');
    } catch (err) {
      console.error(err);
      setError('Unable to create user. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setCurrentUser(null);
    setSelectedRoom(null);
    setError(null);
    localStorage.removeItem(STORAGE_USER);
  };

  const createRoom = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!roomName.trim() || !currentUser) return;

    setLoading(true);
    setError(null);

    try {
      const payload = {
        name: roomName.trim(),
        ownerId: currentUser.id,
      };

      const res = await fetch(`${API_BASE}/rooms`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        throw new Error('Failed to create room');
      }

      const newRoom: Room = await res.json();
      setRooms(prev => [...prev, newRoom]);
      setRoomName('');
    } catch (err) {
      console.error(err);
      setError('Failed to create room. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const enterRoom = (room: Room) => {
    setSelectedRoom(room);
    setMessageText('');
    setError(null);
  };

  const sendMessage = (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedRoom || !currentUser || !messageText.trim()) return;

    const message: ChatMessage = {
      id: `${Date.now()}-${Math.random().toString(36).slice(2)}`,
      roomId: selectedRoom.id,
      userId: currentUser.id,
      userName: currentUser.name,
      text: messageText.trim(),
      createdAt: new Date().toISOString(),
    };

    setMessagesByRoom(prev => {
      const roomMessages = prev[selectedRoom.id] ?? [];
      return {
        ...prev,
        [selectedRoom.id]: [...roomMessages, message],
      };
    });

    setMessageText('');
  };

  const currentRoomMessages = selectedRoom ? messagesByRoom[selectedRoom.id] ?? [] : [];

  return (
    <div className="app-shell">
      <section className="card">
        <header className="header">
          <div>
            <h1>Virtual Study Room</h1>
            <p className="subtitle">Login, join a room, and chat with classmates.</p>
          </div>
          {currentUser && (
            <div className="user-chip">
              Logged in as <strong>{currentUser.name}</strong>
              <button type="button" className="link-button" onClick={logout}>
                Logout
              </button>
            </div>
          )}
        </header>

        {error && <p className="error">{error}</p>}

        {!currentUser ? (
          <form className="form-panel" onSubmit={login}>
            <h2>Sign up</h2>
            <p>Create an account to join the study room app.</p>
            <input
              type="text"
              placeholder="Your name"
              value={userName}
              onChange={e => setUserName(e.target.value)}
              required
            />
            <input
              type="email"
              placeholder="Email address"
              value={userEmail}
              onChange={e => setUserEmail(e.target.value)}
              required
            />
            <input
              type="date"
              placeholder="Date of birth"
              value={userDob}
              onChange={e => setUserDob(e.target.value)}
              required
            />
            <button type="submit" disabled={loading}>
              {loading ? 'Creating account...' : 'Create account'}
            </button>
          </form>
        ) : selectedRoom ? (
          <div className="room-view">
            <div className="room-header">
              <button className="secondary-button" type="button" onClick={() => setSelectedRoom(null)}>
                Back to rooms
              </button>
              <h2>{selectedRoom.name}</h2>
            </div>

            <div className="chat-window">
              {currentRoomMessages.length === 0 ? (
                <div className="empty-state">No chat messages yet. Start the conversation.</div>
              ) : (
                currentRoomMessages.map(message => (
                  <div key={message.id} className="chat-message">
                    <div className="chat-message-header">
                      <span className="chat-author">{message.userName}</span>
                      <span className="chat-time">{new Date(message.createdAt).toLocaleTimeString()}</span>
                    </div>
                    <p>{message.text}</p>
                  </div>
                ))
              )}
            </div>

            <form className="message-form" onSubmit={sendMessage}>
              <input
                type="text"
                placeholder={`Message ${selectedRoom.name}`}
                value={messageText}
                onChange={e => setMessageText(e.target.value)}
                required
              />
              <button type="submit">Send</button>
            </form>
          </div>
        ) : (
          <div className="rooms-panel">
            <form className="form-panel" onSubmit={createRoom}>
              <h2>Create a room</h2>
              <input
                type="text"
                placeholder="Room name"
                value={roomName}
                onChange={e => setRoomName(e.target.value)}
                disabled={loading}
                required
              />
              <button type="submit" disabled={loading}>
                {loading ? 'Creating...' : 'Create room'}
              </button>
            </form>

            <div className="room-list">
              <h2>Available rooms ({rooms.length})</h2>
              {rooms.length === 0 ? (
                <div className="empty-state">No rooms available yet.</div>
              ) : (
                <ul>
                  {rooms.map(room => (
                    <li key={room.id} className="room-item">
                      <div>
                        <strong>{room.name}</strong>
                        <div className="room-meta">Owner ID: {room.ownerId}</div>
                      </div>
                      <button type="button" className="secondary-button" onClick={() => enterRoom(room)}>
                        Join
                      </button>
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </div>
        )}
      </section>
    </div>
  );
}

export default App;
