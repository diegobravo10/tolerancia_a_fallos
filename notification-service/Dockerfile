FROM python:3.11-slim
WORKDIR /app
COPY notification_service.py .
RUN pip install fastapi uvicorn
EXPOSE 8084
CMD ["uvicorn","notification_service:app","--host","0.0.0.0","--port","8084"]
