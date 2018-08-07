
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class SimpleLDAP {
 
    public static void main(String[] args) throws NamingException {
    	//getInfoFromLDAP();
    	//authenticateSearch_need_bind();
    	//authenticateUser("miller", "qqq123");
    	authenticateUser("raso", "qqq123");
    	authenticateUser("sdass", "qqq123");
    }
    
    public static void authenticateUser(String bindDn, String password) {

		String ldapsearchbase = "ou=People,dc=example,dc=com";
    	if(bindDn.equals("sdass")){
       	 bindDn = "userid=" + bindDn + "," + ldapsearchbase;	
    	}else{
    	 bindDn = "sn=" + bindDn + "," + ldapsearchbase;
    	} 
    			
    	Hashtable<String, String> env = new Hashtable<String, String>();
    	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    	env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
    	
    	env.put(Context.SECURITY_AUTHENTICATION, "simple");
    	env.put(Context.SECURITY_PRINCIPAL, bindDn);
    	env.put(Context.SECURITY_CREDENTIALS, password);
    	try {
			InitialDirContext ctx = new InitialDirContext(env);
			System.out.println("user->" + bindDn + " is authenticated . .. .");
			//get other information here
		} catch (NamingException e) {
			System.out.println("user->" + bindDn + " failed authentication . .. .");
			e.printStackTrace();
		}
    	
    	
    }
    

    private static void authenticateSearch_need_bind() throws NamingException{
    	
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
       // env.put(Context.PROVIDER_URL, "ldap://localhost:10389/o=mojo"); //works!!!
        
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        InitialDirContext ctx = new InitialDirContext(env);
        
        //1 person
       // NamingEnumeration<?> namingEnum = ctx.search("sn=miller,ou=people,dc=example,dc=com", "(objectclass=person)", getSimpleSearchControls() );
        NamingEnumeration<?> namingEnum = ctx.search("sn=miller,ou=people,dc=example,dc=com", "(objectclass=person)", getSimpleSearchControls() );
       // NamingEnumeration<?> namingEnum = ctx.search("ou=people", "(objectclass=person)",null );
       
       System.out.println("namingEnum=" + namingEnum != null);
       while(namingEnum.hasMore()){
       	SearchResult entity = (SearchResult) namingEnum.next();
       	
       	System.out.println(entity);
 
       }
       namingEnum.close();
       ctx.close();
    	
    }
    

    public static void getInfoFromLDAP() throws NamingException{    	
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
       // env.put(Context.PROVIDER_URL, "ldap://localhost:10389/o=mojo"); //works!!!
        
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389/dc=example,dc=com");

        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        InitialDirContext ctx = new InitialDirContext(env);

        Attributes attrs = ctx.getAttributes("");
        NamingEnumeration enm = attrs.getAll();

        while (enm.hasMore()) {
            System.out.println(enm.next());
        }
        System.out.println("---------------2------");
       
        //NamingEnumeration<?> namingEnum = ctx.search("ou=people", "(objectclass=person)", getSimpleSearchControls() );
         NamingEnumeration<?> namingEnum = ctx.search("ou=people", "(objectclass=person)",null );
        
        System.out.println("namingEnum=" + namingEnum != null);
        while(namingEnum.hasMore()){
        	SearchResult entity = (SearchResult) namingEnum.next();
        	
        	System.out.println(entity);
        	Attributes attributes = entity.getAttributes();
        	NamingEnumeration<?> attributEnumeration = attributes.getAll();
        	System.out.println("---\n");
        	while(attributEnumeration.hasMore()){
        		System.out.print(attributEnumeration.next() + "> ");
        	}
        	
        }
    	
    }
    private static SearchControls getSimpleSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(30000);
        //String[] attrIDs = {"objectGUID"};
        //searchControls.setReturningAttributes(attrIDs);
        return searchControls;
    }
}
