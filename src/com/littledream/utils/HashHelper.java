package com.littledream.utils;

public class HashHelper {
	public static int SDBMHash(byte[] str)
	{
	    int hash = 0;
	    
	    int index = 0;
	    while (index < str.length)
	    {
	        // equivalent to: hash = 65599*hash + (*str++);
	        hash = (str[index++]) + (hash << 6) + (hash << 16) - hash;
	    }
	 
	    return (hash & 0x7FFFFFFF);
	}
	 
	// RS Hash Function
	public static int RSHash(byte[] str)
	{
	    int b = 378551;
	    int a = 63689;
	    int hash = 0;
	    
	    int index = 0;
	    while (index < str.length)
	    {
	        hash = hash * a + (str[index++]);
	        a *= b;
	    }
	 
	    return (hash & 0x7FFFFFFF);
	}
	 
	// JS Hash Function
	public static  int JSHash(byte[] str)
	{
	    int hash = 1315423911;
	 
	    int index = 0;
	    while (index < str.length)
	    {
	        hash ^= ((hash << 5) + (str[index++]) + (hash >> 2));
	    }
	 
	    return (hash & 0x7FFFFFFF);
	}
	 
	// BKDR Hash Function
	public static int BKDRHash(byte[] str)
	{
	    int seed = 131313; // 31 131 1313 13131 131313 etc..
	    int hash = 0;
	 
	    int index = 0;
	    while (index < str.length)
	    {
	        hash = hash * seed + (str[index++]);
	    }
	 
	    return (hash & 0x7FFFFFFF);
	}
	 
	// DJB Hash Function
	public static int DJBHash(byte[] str)
	{
	    int hash = 5381;
	 
	    int index = 0;
	    while (index < str.length)
	    {
	        hash += (hash << 5) + (str[index ++]);
	    }
	 
	    return (hash & 0x7FFFFFFF);
	}
	 
	// AP Hash Function
	public static int APHash(byte[] str)
	{
	    int hash = 0;
	    
	    int index = 0;
	    while (index < str.length)
	    	if (index%2 == 0)
	    		hash ^= (~((hash << 11) ^ (str[index++]) ^ (hash >> 5)));
	    	else 
	    		hash ^= ((hash << 7) ^ (str[index++]) ^ (hash >> 3));
	    
	    return (hash & 0x7FFFFFFF);
	}
}
