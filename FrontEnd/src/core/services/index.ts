import axios from "axios";
import type { UrlServicesModel } from "./models/UrlServicesModel";

const baseURL = import.meta.env.VITE_API_URL || "https://encurtador-backend.fly.dev";

console.log("🔗 API URL configurada:", baseURL);

export const baseAPI = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json",
  },
});

export async function createShortUrl(
  url: UrlServicesModel.CreateShortUrl.Request
): Promise<UrlServicesModel.CreateShortUrl.Response> {
  const response = await baseAPI.post("/create", { longUrl: url });
  return response.data;
}

export async function getUrlInformation(
  shortUrl: UrlServicesModel.GetUrlInformations.Request
): Promise<UrlServicesModel.GetUrlInformations.Response> {
  const url = new URL(shortUrl).pathname.replace('/', "").trim();
  const response = await baseAPI.get(`/about/${url}`);
  return response.data;
}