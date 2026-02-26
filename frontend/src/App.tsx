import { Route, Routes } from "react-router";
import styles from "./App.module.scss";
import { UsersPage } from "./pages/UsersPage";
import { WebsiteDetailPage } from "./pages/WebsiteDetailPage";
import { WebsitesPage } from "./pages/WebsitesPage";

export function App() {
  return (
    <div className={styles.app}>
      <header className={styles.header}>
        <h1>Interview Challenge</h1>
      </header>
      <main className={styles.main}>
        <Routes>
          <Route path="/" element={<UsersPage />} />
          <Route path="/users/:userId/websites" element={<WebsitesPage />} />
          <Route path="/websites/:websiteId" element={<WebsiteDetailPage />} />
        </Routes>
      </main>
    </div>
  );
}
