
docker run -itd -p 9090:9090/tcp --name=ubuntu-389-ds --privileged ubuntu:jammy /usr/sbin/init

- run apt update -y
- run apt install ufw -y
- run apt install cockpit -y
- open in browser localhost:9090

If it runs perfectly then try setup openldap.