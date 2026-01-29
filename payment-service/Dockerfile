FROM python:3.11-slim
WORKDIR /app
COPY payment_service.py .
RUN pip install fastapi uvicorn
EXPOSE 8083
CMD ["uvicorn","payment_service:app","--host","0.0.0.0","--port","8083"]
