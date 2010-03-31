package org.collectionspace.chain.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.collectionspace.csp.api.ui.UIException;
import org.collectionspace.csp.api.ui.UISession;

public class WebUISession implements UISession {
	private boolean old=false;
	private String id;
	private Random rnd=new Random();
	
	private String randomSession() throws UIException {
		String sessionid=rnd.nextLong()+":"+rnd.nextLong()+":"+System.currentTimeMillis();
        
		byte[] defaultBytes = sessionid.getBytes();
		try{
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();
		            
			StringBuffer hexString = new StringBuffer();
			for (int i=0;i<messageDigest.length;i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]); 
				if(hex.length()==1)
				hexString.append('0');
				hexString.append(hex);
			}
			return hexString+"";
		} catch(NoSuchAlgorithmException nsae){
			throw new UIException("MD5 not supported");
		}
	}
	
	public WebUISession(WebUIUmbrella umbrella) throws UIException {
		id=randomSession();
	}
	
	void setOld() { old=true; }
	boolean isOld() { return old; }
	String getID() { return id; }
}