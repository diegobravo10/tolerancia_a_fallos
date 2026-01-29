from fastapi import FastAPI, HTTPException
import random

app = FastAPI()

@app.post("/notificaciones/email")
def enviar_email(email: str):
    # 80% de probabilidad de fallo
    if random.random() < 0.8:
        print("❌ SMTP caído")
        raise HTTPException(
            status_code=503,
            detail="Servidor SMTP no disponible"
        )

    print(f"Correo enviado a {email}")
    return {"message": "Correo enviado correctamente"}


@app.get("/notificaciones/health")
def health():
    return "OK - notification-service"
