# Frontend Dockerfile - yahan par React application ka Docker image hai
FROM node:18-alpine AS build

# yahan par working directory set kar rahe hain
WORKDIR /app

# yahan par package files copy kar rahe hain
COPY package*.json ./

# yahan par dependencies install kar rahe hain
RUN npm ci --only=production

# yahan par source code copy kar rahe hain
COPY . .

# yahan par application build kar rahe hain
RUN npm run build

# Production stage - yahan par nginx se serve kar rahe hain
FROM nginx:alpine

# yahan par built files copy kar rahe hain
COPY --from=build /app/dist /usr/share/nginx/html

# yahan par nginx configuration copy kar rahe hain
COPY nginx.conf /etc/nginx/nginx.conf

# yahan par non-root user create kar rahe hain
RUN addgroup -g 101 -S nginx && adduser -S -D -H -u 101 -h /var/cache/nginx -s /sbin/nologin -G nginx -g nginx nginx

# yahan par port expose kar rahe hain
EXPOSE 80

# yahan par health check add kar rahe hain
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:80 || exit 1

# yahan par nginx start kar rahe hain
CMD ["nginx", "-g", "daemon off;"]