import AddSubject from "./components/AddSubject";
import StudyPlan from "./components/StudyPlan";

function App() {
  return (
    <div>
      <h1>AI Study Planner</h1>

      <div className="container">
        <div className="section">
          <AddSubject />
        </div>

        <div className="section">
          <StudyPlan />
        </div>
      </div>
    </div>
  );
}

export default App;