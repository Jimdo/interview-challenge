import type {
  CreateDomainRequest,
  CreateUserRequest,
  CreateWebsiteRequest,
  CreateWebsiteVersionRequest,
  Domain,
  User,
  UserListResponse,
  Website,
  WebsiteListResponse,
  WebsiteVersion,
} from "./types";

export class ApiError extends Error {
  constructor(
    public status: number,
    message: string,
  ) {
    super(message);
    this.name = "ApiError";
  }
}

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const response = await fetch(path, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...options?.headers,
    },
  });

  if (!response.ok) {
    const text = await response.text().catch(() => "");
    throw new ApiError(response.status, text || `HTTP ${response.status}`);
  }

  if (response.status === 204) {
    return undefined as T;
  }

  return response.json();
}

// Users

export function listUsers(
  page = 0,
  pageSize = 100,
): Promise<UserListResponse> {
  return request(`/api/users?page=${page}&pageSize=${pageSize}`);
}

export function getUser(id: string): Promise<User> {
  return request(`/api/users/${id}`);
}

export function createUser(data: CreateUserRequest): Promise<User> {
  return request("/api/users", {
    method: "POST",
    body: JSON.stringify(data),
  });
}

export function deleteUser(id: string): Promise<void> {
  return request(`/api/users/${id}`, { method: "DELETE" });
}

// Websites

export function listWebsites(
  userId?: string,
  page = 0,
  pageSize = 100,
): Promise<WebsiteListResponse> {
  const params = new URLSearchParams({
    page: String(page),
    pageSize: String(pageSize),
  });
  if (userId) params.set("userId", userId);
  return request(`/api/websites?${params}`);
}

export function getWebsite(id: string): Promise<Website> {
  return request(`/api/websites/${id}`);
}

export function createWebsite(data: CreateWebsiteRequest): Promise<Website> {
  return request("/api/websites", {
    method: "POST",
    body: JSON.stringify(data),
  });
}

export function deleteWebsite(id: string): Promise<void> {
  return request(`/api/websites/${id}`, { method: "DELETE" });
}

// Website Versions (backend controllers not yet implemented)

export function listVersions(websiteId: string): Promise<WebsiteVersion[]> {
  return request(`/api/websites/${websiteId}/versions`);
}

export function createVersion(
  websiteId: string,
  data: CreateWebsiteVersionRequest,
): Promise<WebsiteVersion> {
  return request(`/api/websites/${websiteId}/versions`, {
    method: "POST",
    body: JSON.stringify(data),
  });
}

export function publishVersion(
  websiteId: string,
  versionId: string,
): Promise<void> {
  return request(`/api/websites/${websiteId}/versions/${versionId}/publish`, {
    method: "POST",
  });
}

// Domains (backend controllers not yet implemented)

export function listDomains(websiteId: string): Promise<Domain[]> {
  return request(`/api/websites/${websiteId}/domains`);
}

export function createDomain(
  websiteId: string,
  data: CreateDomainRequest,
): Promise<Domain> {
  return request(`/api/websites/${websiteId}/domains`, {
    method: "POST",
    body: JSON.stringify(data),
  });
}

export function deleteDomain(
  websiteId: string,
  domain: string,
): Promise<void> {
  return request(`/api/websites/${websiteId}/domains/${domain}`, {
    method: "DELETE",
  });
}
