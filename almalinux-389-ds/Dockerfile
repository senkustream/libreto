FROM almalinux:9
RUN /usr/bin/dnf install epel-release -y \
    && /usr/bin/dnf update -y \
    && /usr/bin/dnf install langpacks-en glibc-all-langpacks -y \
    && /usr/bin/dnf config-manager --set-enable crb \
    && /usr/bin/dnf install firewalld -y \
    && /usr/bin/dnf copr enable @389ds/389-directory-server -y \
    && /usr/bin/dnf install 389-ds-base cockpit-389-ds -y \
    && /usr/bin/dnf install passwd -y