import { useState } from "react";
import axios from "axios";

function StudyPlan() {
  const [dailyHours, setDailyHours] = useState("");
  const [plans, setPlans] = useState([]);

  const generatePlan = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8080/plan/generate-multi?userId=3&dailyHours=${dailyHours}`
      );
      console.log(response.data);
      setPlans(response.data);
    } catch (error) {
      console.error(error);
      alert("Error generating plan");
    }
  };

  return (
    <div>
      <h2>Generate Study Plan</h2>

      <input
        type="number"
        placeholder="Daily Study Hours"
        value={dailyHours}
        onChange={(e) => setDailyHours(e.target.value)}
      />

      <br /><br />

      <button onClick={generatePlan}>Generate Plan</button>

      <hr />

      <h2>Study Plan</h2>

      {plans.length === 0 ? (
        <p>No plan generated</p>
      ) : (
        <table border="1">
          <thead>
            <tr>
              <th>Date</th>
              <th>Subject</th>
              <th>Hours</th>
            </tr>
          </thead>
          <tbody>
            {plans.map((plan) => (
              <tr key={plan.id}>
                <td>{plan.date}</td>
                <td>{plan.subject?.name}</td>
                <td>{plan.hoursAllocated}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default StudyPlan;