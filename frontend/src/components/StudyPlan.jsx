import { useState } from "react";
import axios from "axios";

function StudyPlan() {
  const [dailyHours, setDailyHours] = useState("");
  const [plans, setPlans] = useState([]);
  const [aiResponse, setAiResponse] = useState("");
  const [loadingAI, setLoadingAI] = useState(false);

  
  const generatePlan = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8080/plan/generate-multi?userId=3&dailyHours=${dailyHours}`
      );

      if (Array.isArray(response.data)) {
        setPlans(response.data);
        setAiResponse(""); 
      } else {
        setPlans([]);
      }
    } catch (error) {
      console.error(error);
      alert("Error generating plan");
    }
  };

  
  const getAISuggestions = async () => {
    try {
      setLoadingAI(true);

      const subjectsMap = {};

      plans.forEach((plan) => {
        const sub = plan.subject;
        if (!sub) return;

        if (!subjectsMap[sub.name]) {
          const today = new Date();
          const deadline = new Date(sub.deadline);
          const daysLeft = Math.ceil(
            (deadline - today) / (1000 * 60 * 60 * 24)
          );

          subjectsMap[sub.name] = {
            difficulty: sub.difficulty,
            daysLeft: daysLeft > 0 ? daysLeft : 1,
          };
        }
      });

      let prompt = "Generate a study strategy:\n";

      Object.keys(subjectsMap).forEach((name, index) => {
        const s = subjectsMap[name];
        prompt += `${index + 1}. ${name} - difficulty ${s.difficulty} - ${s.daysLeft} days left\n`;
      });

      prompt += "Give priority and time management tips.";

    
      const aiRes = await axios.get(
        "http://localhost:8080/ai/recommend",
        { params: { prompt } }
      );

     
      const text =
        aiRes.data?.candidates?.[0]?.content?.parts?.[0]?.text || "";

      setAiResponse(text);
    } catch (error) {
      console.error(error);
      alert("Error fetching AI suggestions");
    } finally {
      setLoadingAI(false);
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

      {!plans || plans.length === 0 ? (
        <p>No plan generated</p>
      ) : (
        <>
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
                  <td>{plan.subject ? plan.subject.name : "N/A"}</td>
                  <td>{plan.hoursAllocated}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <br />

        
          <button onClick={getAISuggestions}>
            Get AI Suggestions 🤖
          </button>
        </>
      )}

      <hr />

      <h2>🤖 AI Suggestions</h2>

      {loadingAI ? (
        <p>Fetching suggestions...</p>
      ) : aiResponse ? (
        <pre style={{ whiteSpace: "pre-wrap" }}>{aiResponse}</pre>
      ) : (
        <p>No suggestions yet</p>
      )}
    </div>
  );
}

export default StudyPlan;