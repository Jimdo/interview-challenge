import { useState } from "react";
import { Link, useParams } from "react-router";
import { createWebsite, deleteWebsite, listWebsites } from "../api/client";
import { useApi } from "../hooks/useApi";
import styles from "./WebsitesPage.module.scss";

export function WebsitesPage() {
  const { userId } = useParams<{ userId: string }>();
  const { data, error, loading, refetch } = useApi(
    () => listWebsites(userId),
    [userId],
  );
  const [formError, setFormError] = useState<string | null>(null);

  async function handleCreate() {
    if (!userId) return;
    setFormError(null);
    try {
      await createWebsite({ userId });
      refetch();
    } catch (err) {
      setFormError(err instanceof Error ? err.message : "Failed to create");
    }
  }

  async function handleDelete(id: string) {
    try {
      await deleteWebsite(id);
      refetch();
    } catch (err) {
      setFormError(err instanceof Error ? err.message : "Failed to delete");
    }
  }

  return (
    <div>
      <Link to="/">← Back to Users</Link>
      <h2>Websites</h2>
      <p className={styles.subtitle}>User: {userId}</p>

      <button className={styles.createButton} onClick={handleCreate}>
        Create Website
      </button>

      {formError && <p className={styles.error}>{formError}</p>}
      {loading && <p>Loading…</p>}
      {error && <p className={styles.error}>{error.message}</p>}

      {data && (
        <table className={styles.table}>
          <thead>
            <tr>
              <th>Website ID</th>
              <th>Created</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {data.websites.map((website) => (
              <tr key={website.id}>
                <td>
                  <Link to={`/websites/${website.id}`}>{website.id}</Link>
                </td>
                <td>{new Date(website.createdAt).toLocaleDateString()}</td>
                <td>
                  <button
                    className={styles.deleteButton}
                    onClick={() => handleDelete(website.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
            {data.websites.length === 0 && (
              <tr>
                <td colSpan={3}>No websites yet.</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
}
