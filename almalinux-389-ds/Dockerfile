FROM almalinux:9
RUN /usr/bin/dnf install epel-release -y \
    && /usr/bin/dnf update -y \
    && /usr/bin/dnf install langpacks-en glibc-all-langpacks -y \
    && /usr/bin/dnf config-manager --set-enable crb \
    && /usr/bin/dnf install firewalld -y \
    && /usr/bin/dnf copr enable @389ds/389-directory-server -y \
    && /usr/bin/dnf install 389-ds-base cockpit-389-ds -y \
    && /usr/bin/dnf install passwd -y
# Disable ds_systemd_ask_password_acl invocation together: https://github.com/freeipa/freeipa-container/issues/189#issuecomment-557457878
RUN ( echo "[Service]" ; echo "ExecStartPre=" ) > /usr/lib/systemd/system/dirsrv@.service.d/no-setfacl.conf