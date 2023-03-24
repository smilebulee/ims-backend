# ims-backend
openJDK 11

    MAVEN 빌드시 SSL HANDSHAKE 발생하면 keytool을 이용하여 해당 도메인의 인증서를 설치해줘야 함.    
    ex) keytool -importcert -alias 등록할 별칭 -keystore JAVA_HOME/lib/security/cacerts -file 루트인증서

Springboot 2.7.9

    동일한 서버의 REACT 3000 번 포트에 대해 CORS 설정.

jpa

    Springboot기동시 기존 테이블 삭제 생성하지 않도록 application.yml에 spring.jpa.generate-ddl: false 추가

mariaDB

    설치 후 포트변경(필요시) 및 캐릭터셋 세팅, 방화벽 해제 필요
    $ vi client.cnf
      [client]
      default-character-set=utf8

    $ vi mysql-client.cnf
      [mysql]
      default-character-set = utf8
      [mysqldump]
      default-character-set=utf8

    $ vi server.cnf
      [mysqld]
      port=3309
      init_connect="SET collation_connection=utf8_general_ci"
      init_connect="SET NAMES utf8"
      character-set-server = utf8
      collation-server = utf8_general_ci
      lower_case_table_names=1      -> 대소문자 구분 안하기, 0이면 A와 a는 다른 객체가 되어 버림

    $ sestatus
      SELinux status: enabled   -> enabled되어 있는지 확인
      
    $ semanage port -l | grep mysqld_port_t
      mysqld_port_t                  tcp      1186, 3306, 63132-63164
    $ semanage port -a -t mysqld_port_t -p tcp 3309   -> 포트 추가
    $ semanage port -l | grep mysqld_port_t
      mysqld_port_t                  tcp      3309, 1186, 3306, 63132-63164
      
    $ systemctl start mariadb    -> 기동
    $ systemctl enable mariadb  -> 시작시 자동실행 등록
    
    $ firewall-cmd --permanent --add-port=3309/tcp   -> 방화벽개방
    $ firewall-cmd --reload


