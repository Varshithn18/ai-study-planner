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
        "http://localhost:8080/subjects?userId=3",
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

      <form onSubmit={handleSubmit} style={{ marginTop: "15px" }}>
        
        <label>Subject Name</label>
        <input
          type="text"
          placeholder="Enter subject name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />

        <br /><br />

        <label>Difficulty (1-5)</label>
        <input
          type="number"
          min="1"
          max="5"
          placeholder="Enter difficulty"
          value={difficulty}
          onChange={(e) => setDifficulty(e.target.value)}
          required
        />

        <br /><br />

        <label>Exam Date</label>
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