import { type FormEvent, useState } from "react";
import { Link, useParams } from "react-router";
import {
  createDomain,
  createVersion,
  deleteDomain,
  deleteWebsite,
  getWebsite,
  listDomains,
  listVersions,
  publishVersion,
} from "../api/client";
import { useApi } from "../hooks/useApi";
import styles from "./WebsiteDetailPage.module.scss";

export function WebsiteDetailPage() {
  const { websiteId } = useParams<{ websiteId: string }>();

  const {
    data: website,
    error: websiteError,
    loading: websiteLoading,
  } = useApi(() => getWebsite(websiteId!), [websiteId]);

  const {
    data: versions,
    error: versionsError,
    loading: versionsLoading,
    refetch: refetchVersions,
  } = useApi(() => listVersions(websiteId!), [websiteId]);

  const {
    data: domains,
    error: domainsError,
    loading: domainsLoading,
    refetch: refetchDomains,
  } = useApi(() => listDomains(websiteId!), [websiteId]);

  const [payload, setPayload] = useState("");
  const [domainName, setDomainName] = useState("");
  const [actionError, setActionError] = useState<string | null>(null);
  const [deleted, setDeleted] = useState(false);

  async function handleCreateVersion(e: FormEvent) {
    e.preventDefault();
    if (!websiteId) return;
    setActionError(null);
    try {
      await createVersion(websiteId, { payload });
      setPayload("");
      refetchVersions();
    } catch (err) {
      setActionError(err instanceof Error ? err.message : "Failed to create");
    }
  }

  async function handlePublish(versionId: string) {
    if (!websiteId) return;
    setActionError(null);
    try {
      await publishVersion(websiteId, versionId);
      refetchVersions();
    } catch (err) {
      setActionError(err instanceof Error ? err.message : "Failed to publish");
    }
  }

  async function handleCreateDomain(e: FormEvent) {
    e.preventDefault();
    if (!websiteId) return;
    setActionError(null);
    try {
      await createDomain(websiteId, { domain: domainName });
      setDomainName("");
      refetchDomains();
    } catch (err) {
      setActionError(err instanceof Error ? err.message : "Failed to create");
    }
  }

  async function handleDeleteDomain(domain: string) {
    if (!websiteId) return;
    setActionError(null);
    try {
      await deleteDomain(websiteId, domain);
      refetchDomains();
    } catch (err) {
      setActionError(err instanceof Error ? err.message : "Failed to delete");
    }
  }

  async function handleDeleteWebsite() {
    if (!websiteId) return;
    try {
      await deleteWebsite(websiteId);
      setDeleted(true);
    } catch (err) {
      setActionError(err instanceof Error ? err.message : "Failed to delete");
    }
  }

  if (deleted) {
    return (
      <div>
        <p>Website deleted.</p>
        <Link to="/">Back to Users</Link>
      </div>
    );
  }

  return (
    <div>
      <Link to={website ? `/users/${website.userId}/websites` : "/"}>
        ← Back
      </Link>

      <h2>Website Detail</h2>

      {actionError && <p className={styles.error}>{actionError}</p>}

      <section className={styles.section}>
        <h3>Info</h3>
        {websiteLoading && <p>Loading…</p>}
        {websiteError && <p className={styles.error}>{websiteError.message}</p>}
        {website && (
          <dl className={styles.info}>
            <dt>ID</dt>
            <dd>{website.id}</dd>
            <dt>User ID</dt>
            <dd>{website.userId}</dd>
            <dt>Created</dt>
            <dd>{new Date(website.createdAt).toLocaleString()}</dd>
            <dt>Updated</dt>
            <dd>{new Date(website.updatedAt).toLocaleString()}</dd>
          </dl>
        )}
        <button className={styles.deleteButton} onClick={handleDeleteWebsite}>
          Delete Website
        </button>
      </section>

      <section className={styles.section}>
        <h3>Versions</h3>
        <form className={styles.form} onSubmit={handleCreateVersion}>
          <textarea
            placeholder="Version payload (HTML, JSON, etc.)"
            value={payload}
            onChange={(e) => setPayload(e.target.value)}
            rows={3}
            required
          />
          <button type="submit">Create Version</button>
        </form>
        {versionsLoading && <p>Loading…</p>}
        {versionsError && (
          <p className={styles.error}>{versionsError.message}</p>
        )}
        {versions && versions.length > 0 && (
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Version</th>
                <th>Payload</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {versions.map((v) => (
                <tr key={v.id}>
                  <td>{v.versionNumber}</td>
                  <td className={styles.payload}>
                    {v.payload.length > 80
                      ? v.payload.slice(0, 80) + "…"
                      : v.payload}
                  </td>
                  <td>
                    <button
                      className={styles.publishButton}
                      onClick={() => handlePublish(v.id)}
                    >
                      Publish
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
        {versions && versions.length === 0 && <p>No versions yet.</p>}
      </section>

      <section className={styles.section}>
        <h3>Domains</h3>
        <form className={styles.form} onSubmit={handleCreateDomain}>
          <input
            type="text"
            placeholder="example.com"
            value={domainName}
            onChange={(e) => setDomainName(e.target.value)}
            required
          />
          <button type="submit">Add Domain</button>
        </form>
        {domainsLoading && <p>Loading…</p>}
        {domainsError && (
          <p className={styles.error}>{domainsError.message}</p>
        )}
        {domains && domains.length > 0 && (
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Domain</th>
                <th>Created</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {domains.map((d) => (
                <tr key={d.domain}>
                  <td>{d.domain}</td>
                  <td>{new Date(d.createdAt).toLocaleDateString()}</td>
                  <td>
                    <button
                      className={styles.deleteButton}
                      onClick={() => handleDeleteDomain(d.domain)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
        {domains && domains.length === 0 && <p>No domains yet.</p>}
      </section>
    </div>
  );
}
