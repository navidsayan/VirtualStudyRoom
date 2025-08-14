import { useState, useEffect } from 'react';
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'


interface Room {
  id: number;
  name: string;
}

function App() {
  const [rooms, setRooms] = useState<Room[]>([]);
  const [roomName, setRoomName] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
      fetch('/api/v1/rooms')
        .then(res => res.json())
        .then(data => setRooms(data))
        .catch(err => console.error('Error fetching rooms:', err));
  }, []);

   // Handle create room submit
const createRoom = async (e: React.FormEvent) => {
  e.preventDefault();
  if (!roomName.trim()) return;

  setLoading(true);
  try {
    const res = await fetch('http://localhost:8080/api/v1/rooms', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
          name: roomName,

          owner: { id: 3, name: "Mariam", dob: "2000-01-05",  email: "mariam.jamal@gmail.com" }
      }),
    });
    if (res.ok) {
      const newRoom = await res.json();
      setRooms([...rooms, newRoom]);
      setRoomName('');
    } else {
      alert('Failed to create room');
    }
  } catch (error) {
    console.error(error);
    alert('Error creating room');
  }
  setLoading(false);
};

  return (
    <>
      <div style={{ maxWidth: 600, margin: 'auto', padding: 20 }}>
            <h1>Virtual Study Rooms</h1>
            <form onSubmit={createRoom}>
              <input
                type="text"
                placeholder="Room name"
                value={roomName}
                onChange={e => setRoomName(e.target.value)}
                disabled={loading}
                required
                style={{ padding: 8, width: '70%', marginRight: 8 }}
              />
              <button type="submit" disabled={loading}>
                {loading ? 'Creating...' : 'Create Room'}
              </button>
            </form>

            <h2>Available Rooms</h2>
            <ul>
              {rooms.length === 0 ? (
                <li>No rooms yet</li>
              ) : (
                rooms.map(room => <li key={room.id}>{room.name}</li>)
              )}
            </ul>
      </div>
    </>
  )
}

export default App
