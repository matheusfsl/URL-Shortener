// App.jsx
import { Suspense } from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import LinkInformation from "./pages/LinkInformation";

export default function App() {
  return (
    <Suspense fallback={<div>Carregando...</div>}>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/information" element={<LinkInformation />} />
      </Routes>
    </Suspense>
  );
}
