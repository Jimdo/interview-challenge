export interface User {
  id: string;
  email: string;
  createdAt: string;
  updatedAt: string;
}

export interface UserListResponse {
  users: User[];
  totalCount: number;
  pageSize: number;
  page: number;
  totalPages: number;
}

export interface CreateUserRequest {
  email: string;
}

export interface Website {
  id: string;
  userId: string;
  createdAt: string;
  updatedAt: string;
}

export interface WebsiteListResponse {
  websites: Website[];
  totalCount: number;
  pageSize: number;
  page: number;
  totalPages: number;
}

export interface CreateWebsiteRequest {
  userId: string;
}

export interface WebsiteVersion {
  id: string;
  websiteId: string;
  versionNumber: number;
  payload: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateWebsiteVersionRequest {
  payload: string;
}

export interface Domain {
  domain: string;
  websiteId: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateDomainRequest {
  domain: string;
}
