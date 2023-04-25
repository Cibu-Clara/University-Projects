const PROD_BACKEND_API_URL = "/api";
const DEV_BACKEND_API_URL = "http://ec2-13-50-99-89.eu-north-1.compute.amazonaws.com/api";
export const BACKEND_API_URL =
	process.env.NODE_ENV === "development" ? DEV_BACKEND_API_URL : PROD_BACKEND_API_URL;
