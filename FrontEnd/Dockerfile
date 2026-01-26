# ======================
# BUILD
# ======================
FROM node:18 AS build
WORKDIR /app

# ⚠️ IMPORTANTE: Definir a URL da API ANTES do npm install
ARG VITE_API_URL=https://encurtador-backend.fly.dev
ENV VITE_API_URL=$VITE_API_URL

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# ======================
# RUNTIME
# ======================
FROM nginx:alpine

RUN rm /etc/nginx/conf.d/default.conf

COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]