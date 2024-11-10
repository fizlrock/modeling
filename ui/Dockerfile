# Шаг 1: Используем официальный образ Node.js для сборки
FROM node:18 as build

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем все файлы в контейнер
COPY . .

# Собираем приложение для продакшена
RUN npm run build

# Шаг 2: Используем Nginx для сервировки собранного приложения
FROM nginx:alpine

# Копируем собранные файлы из предыдущего контейнера в папку Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Копируем конфигурацию Nginx (опционально, можно оставить дефолтную)
# COPY nginx.conf /etc/nginx/nginx.conf

# Открываем порт
EXPOSE 80

# Запускаем Nginx
CMD ["nginx", "-g", "daemon off;"]
