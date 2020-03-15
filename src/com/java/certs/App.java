package com.java.certs;
import com.sun.jna.Function;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class App {
	
	public String showDialogAndGetCertsName() {
		try {
			NativeLibrary cryptUI = NativeLibrary.getInstance("Cryptui");
		    NativeLibrary crypt32 = NativeLibrary.getInstance("Crypt32");

		    Function functionCertOpenSystemStore = crypt32.getFunction("CertOpenSystemStoreA");
		    Object[] argsCertOpenSystemStore = new Object[] { 0, "CA"};
		    HANDLE h = (HANDLE) functionCertOpenSystemStore.invoke(HANDLE.class, argsCertOpenSystemStore);

		    Function functionCryptUIDlgSelectCertificateFromStore = cryptUI.getFunction("CryptUIDlgSelectCertificateFromStore");
		    System.out.println(functionCryptUIDlgSelectCertificateFromStore.getName());
		    Object[] argsCryptUIDlgSelectCertificateFromStore = new Object[] { h, 0, 0, 0, 16, 0, 0};
		    Pointer ptrCertContext = (Pointer) functionCryptUIDlgSelectCertificateFromStore.invoke(Pointer.class, argsCryptUIDlgSelectCertificateFromStore);

		    String selectedCert = null;
		    if(ptrCertContext != null) {
		    	Function functionCertGetNameString = crypt32.getFunction("CertGetNameStringW");
			    char[] ptrName = new char[128];
			    Object[] argsCertGetNameString = new Object[] { ptrCertContext, 5, 0, 0, ptrName, 128};
			    functionCertGetNameString.invoke(argsCertGetNameString);
			    selectedCert = new String(ptrName).trim();
			    String outputText = "Selected certificate is [" +   new String(ptrName).trim() + "]";
			    System.out.println(outputText);

			    Function functionCertFreeCertificateContext = crypt32.getFunction("CertFreeCertificateContext");
			    Object[] argsCertFreeCertificateContext = new Object[] { ptrCertContext};
			    functionCertFreeCertificateContext.invoke(argsCertFreeCertificateContext);
		    }
		    
		    Function functionCertCloseStore = crypt32.getFunction("CertCloseStore");
		    Object[] argsCertCloseStore = new Object[] { h, 0};
		    functionCertCloseStore.invoke(argsCertCloseStore);
		    
		    return selectedCert;
		}
		catch (Exception exception) {  
	        System.out.println("Não foi possível selecionar o certificado digital!" +  
	                exception);
	    }
	      
	    return null; 
	}
	
	public String selecionar() {  
	    try {  
	        NativeLibrary cryptUI = NativeLibrary.getInstance("Cryptui");  
	        NativeLibrary crypt32 = NativeLibrary.getInstance("Crypt32");  
	          
	        Function functionCertOpenSystemStore = crypt32.getFunction("CertOpenSystemStoreA");  
	        Object[] argsCertOpenSystemStore = new Object[] { 0, "MY"};  
	        HANDLE handle = (HANDLE) functionCertOpenSystemStore.invoke(HANDLE.class,  
	                                                                    argsCertOpenSystemStore);  
	          
	        Function functionCryptUIDlgSelectCertificateFromStore = cryptUI  
	                .getFunction("CryptUIDlgSelectCertificateFromStore");  
	        Object[] argsCryptUIDlgSelectCertificateFromStore =  
	                new Object[] { handle, 0, 0, 0, 16, 0, 0};  
	        Pointer pointerCertContext = (Pointer) functionCryptUIDlgSelectCertificateFromStore  
	                .invoke(Pointer.class, argsCryptUIDlgSelectCertificateFromStore);  
	          
	        String idCertificadoSelecionado = null;  
	        if (pointerCertContext != null) {  
	            Function functionCertGetNameString = crypt32.getFunction("CertGetNameStringW");  
	            char[] pointerName = new char[1024];  
	            Object[] argsCertGetNameString =  
	                    new Object[] { pointerCertContext, 5, 0, 0, pointerName, 1024};  
	            functionCertGetNameString.invoke(argsCertGetNameString);  
	            idCertificadoSelecionado = pointerName.toString().trim();  
	              
	            Function functionCertFreeCertificateContext = crypt32  
	                    .getFunction("CertFreeCertificateContext");  
	            Object[] argsCertFreeCertificateContext = new Object[] { pointerCertContext};  
	            functionCertFreeCertificateContext.invoke(argsCertFreeCertificateContext);  
	        }
	          
	        Function functionCertCloseStore = crypt32.getFunction("CertCloseStore");  
	        Object[] argsCertCloseStore = new Object[] { handle, 0};  
	        functionCertCloseStore.invoke(argsCertCloseStore);  
	          
	        return idCertificadoSelecionado;  
	    }
	    catch (Exception exception) {  
	        System.out.println("Não foi possível selecionar o certificado digital!" +  
	                exception);
	    }
	      
	    return null;  
	}

}
