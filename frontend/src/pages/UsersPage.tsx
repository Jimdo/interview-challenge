import { type FormEvent, useState } from "react";
import { Link } from "react-router";
import { createUser, deleteUser, listUsers } from "../api/client";
import { useApi } from "../hooks/useApi";
import styles from "./UsersPage.module.scss";

export function UsersPage() {
  const { data, error, loading, refetch } = useApi(() => listUsers(), []);
  const [email, setEmail] = useState("");
  const [formError, setFormError] = useState<string | null>(null);

  async function handleCreate(e: FormEvent) {
    e.preventDefault();
    setFormError(null);
    try {
      await createUser({ email });
      setEmail("");
      refetch();
    } catch (err) {
      setFormError(err instanceof Error ? err.message : "Failed to create");
    }
  }

  async function handleDelete(id: string) {
    try {
      await deleteUser(id);
      refetch();
    } catch (err) {
      setFormError(err instanceof Error ? err.message : "Failed to delete");
    }
  }

  return (
    <div>
      <h2>Users</h2>

      <form className={styles.form} onSubmit={handleCreate}>
        <input
          type="email"
          placeholder="Email address"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <button type="submit">Create User</button>
      </form>

      {formError && <p className={styles.error}>{formError}</p>}
      {loading && <p>Loading…</p>}
      {error && <p className={styles.error}>{error.message}</p>}

      {data && (
        <table className={styles.table}>
          <thead>
            <tr>
              <th>Email</th>
              <th>Created</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {data.users.map((user) => (
              <tr key={user.id}>
                <td>
                  <Link to={`/users/${user.id}/websites`}>{user.email}</Link>
                </td>
                <td>{new Date(user.createdAt).toLocaleDateString()}</td>
                <td>
                  <button
                    className={styles.deleteButton}
                    onClick={() => handleDelete(user.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
            {data.users.length === 0 && (
              <tr>
                <td colSpan={3}>No users yet.</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
}
