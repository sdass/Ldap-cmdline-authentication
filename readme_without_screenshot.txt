For LDAP java testing with Apache Directory Service  
start here: https://www.stefan-seelmann.de/blog/setting-up-an-ldap-server-for-your-development-environment
ldif loading is failing. To do that manually add data or certain top level domain as in step (3).

(1) Setup Apache DS first. It comes with Apache Directory Studio as screenshot. It will be with APACHE DS server (LDAP protocol). Create server. Start serverserver. Will listen to 10389 (Overview tab or LDAP/LDAPS Servers tab). Partitions tab create partition (high level domain)
(2) Create Connections on Connections tab (righthand side). See the node on LDAP Browser left right tab
(3) Add data (Entry) manually right clicking dc=example, doc=com node. Sn=miller or userid=sdass as in the screenshot


(4) stop start server on LDAP Servers tab (left bottom corner). Choose and Refresh one Connection on Connections tab (left bottom corner) to appear on LDAP Browser pane.
***** step 5 ****** is the most important ******
(5) Code java application to connect as admin, authenticate as any user, get SubTree and print the enum content (File name SimpleLDAP.java)
C:\Temp\aa>javac SimpleLDAP.java
C:\Temp\aa>java SimpleLDAP
C:\Temp\aa>java SimpleLDAP
user->sn=raso,ou=People,dc=example,dc=com is authenticated . .. .
user->userid=sdass,ou=People,dc=example,dc=com is authenticated . .. .

<<screenshot Apache DS studio on doc file >>

May explore more uncommenting other lines on main()
    	//getInfoFromLDAP();
    	//authenticateSearch_need_bind();
    	//authenticateUser("miller", "qqq123");
 (6) May play with .ldif file (LDAP data interchange format). A new line at the end must. Can be imported from ApacheDS admin console. Or LDAP-> New LDIF file. Cut past content. Browse tab on top righ corner for connection. Then top green triangle to load/import user data.
# Entry for Fletcher Christian
#
dn: cn=Fletcher Christian,ou=people,o=sevenSeas
cn: Fletcher Christian
objectClass: top
objectClass: person
objectClass: organizationalPerson
objectClass: inetOrgPerson
sn: Christian
givenName: Fletcher
description: Lieutenant Fletcher Christian
manager: cn=William Bligh,ou=people,o=sevenSeas
