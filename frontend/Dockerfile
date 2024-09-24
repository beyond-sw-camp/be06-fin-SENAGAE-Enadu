FROM nginx:latest
ADD ./nginx.conf /etc/nginx/nginx.conf
ADD ./dist/css /usr/share/nginx/html/css
ADD ./dist/js /usr/share/nginx/html/js
#ADD ./dist/img /usr/share/nginx/html/img
RUN rm -rf /usr/share/nginx/html/index.html
ADD ./dist/index.html /usr/share/nginx/html/index.html
#ADD ./dist/icon /usr/share/nginx/html/icon
CMD ["nginx", "-g", "daemon off;"]