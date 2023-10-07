cd ./pennywise_container
docker rmi -f mariadb:latest
docker rmi -f pennywise:latest
docker load < pennywise.tar
docker load < mariadb.tar 
docker load < pennywise-ui.tar
docker-compose -f docker-compse.yml up

