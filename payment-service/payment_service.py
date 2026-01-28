from fastapi import FastAPI, HTTPException
import time

app = FastAPI()

# Flags para simular escenarios
SLOW_MODE = True
PAYMENT_AVAILABLE = True

@app.post("/payments/charge")
def charge(reservationId: str, amount: float):

    if not PAYMENT_AVAILABLE:
        raise HTTPException(status_code=503, detail="Pasarela caída")

    if SLOW_MODE:
        print("⏳ Procesando pago lentamente...")
        time.sleep(20)  # 20 segundos de latencia

    print(f"💰 Pago aprobado - reserva {reservationId}, monto {amount}")
    return True


@app.post("/payments/slow")
def slow(enabled: bool):
    global SLOW_MODE
    SLOW_MODE = enabled
    return {"slow_mode": SLOW_MODE}


@app.post("/payments/toggle")
def toggle(available: bool):
    global PAYMENT_AVAILABLE
    PAYMENT_AVAILABLE = available
    return {"payment_available": PAYMENT_AVAILABLE}


@app.get("/payments/health")
def health():
    return "OK - payment-service"
