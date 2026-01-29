from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse
import httpx

app = FastAPI()

RESERVATION_URL = "http://reservation-service:8081"
NOTIFICATION_URL = "http://notification-service:8084"

client = httpx.AsyncClient(timeout=5.0)

# =========================
# RESERVATIONS
# =========================
@app.api_route("/reservations/{path:path}", methods=["GET", "POST"])
async def reservations_proxy(path: str, request: Request):
    try:
        url = f"{RESERVATION_URL}/reservations/{path}"
        response = await client.request(
            request.method,
            url,
            params=request.query_params,
            json=await request.json() if request.method == "POST" else None
        )
        return JSONResponse(status_code=response.status_code, content=response.json())
    except Exception:
        return JSONResponse(
            status_code=503,
            content={"message": "Servicio de reservas no disponible"}
        )

# =========================
# NOTIFICATIONS
# =========================
@app.api_route("/notificaciones/{path:path}", methods=["GET", "POST"])
async def notifications_proxy(path: str, request: Request):
    try:
        url = f"{NOTIFICATION_URL}/notificaciones/{path}"
        response = await client.request(
            request.method,
            url,
            params=request.query_params
        )
        return JSONResponse(status_code=response.status_code, content=response.json())
    except Exception:
        return JSONResponse(
            status_code=503,
            content={"message": "Servicio de notificaciones no disponible"}
        )

# =========================
# HEALTH
# =========================
@app.get("/health")
def health():
    return "OK - api-gateway"
