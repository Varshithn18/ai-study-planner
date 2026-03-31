import { useState } from "react";
import axios from "axios";

function AddSubject() {
  const [name, setName] = useState("");
  const [difficulty, setDifficulty] = useState("");
  const [deadline, setDeadline] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const subject = {
      name,
      difficulty: parseInt(difficulty),
      deadline,
    };

    try {
      await axios.post(
        "http://localhost:8080/subjects?userId=1",
        subject
      );

      alert("Subject added!");

      // clear form
      setName("");
      setDifficulty("");
      setDeadline("");
    } catch (error) {
      console.error(error);
      alert("Error adding subject");
    }
  };

  return (
    <div>
      <h2>Add Subject</h2>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Subject Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />

        <br /><br />

        <input
          type="number"
          placeholder="Difficulty (1-5)"
          value={difficulty}
          onChange={(e) => setDifficulty(e.target.value)}
          required
        />

        <br /><br />

        <input
          type="date"
          value={deadline}
          onChange={(e) => setDeadline(e.target.value)}
          required
        />

        <br /><br />

        <button type="submit">Add Subject</button>
      </form>
    </div>
  );
}

export default AddSubject;