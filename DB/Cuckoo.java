package com;
public class Cuckoo{ 

	static int hash[][];
	static int pos[];
	static int MAXN;
	static int ver;

public static void initTable(int size) {
	MAXN = size;
	ver = 2;
	hash = new int[ver][size];
	pos = new int[ver];
	
	for(int j=0;j<MAXN;j++){
		for(int i=0;i<ver;i++){
			hash[i][j] = -1;
		}
	}
}

public static int hash(int function, int key){
	int value = 0;
	switch (function){
		case 1: {
			value =  key%MAXN;
			break;
		}
        case 2: {
			value = (key/MAXN)%MAXN;
			break;
		}
    }
	return value;
}

public static void place(int key, int tableID, int cnt, int n){
	if (cnt==n){
		System.out.println("unpositioned "+key);
        System.out.println("Cycle present. REHASH.");
        return;
    }
 
    for(int i=0; i<ver; i++) {
		pos[i] = hash(i+1, key);
        if(hash[i][pos[i]] == key)
			return;
    }
 
    if(hash[tableID][pos[tableID]] != -1){
		int dis = hash[tableID][pos[tableID]];
        hash[tableID][pos[tableID]] = key;
        place(dis, (tableID+1)%ver, cnt+1, n);
    }else{
		hash[tableID][pos[tableID]] = key;
	}
}

public static void printTable(){
	System.out.println("Final hash tables:");
	int count = 0;
	for(int i=0;i<ver;i++){
		for(int j=0;j<MAXN;j++){
			if(hash[i][j] == -1){
				System.out.print("- ");
			}
			if(hash[i][j] != -1){
				System.out.print(hash[i][j]+" ");
				count = count + 1;
			}
		}
		System.out.println();
	}
	System.out.println(count);
}

public static void cuckoo(int keys[], int n){
	initTable(keys.length);
	for(int i=0,cnt=0;i<n;i++,cnt=0){
		place(keys[i], 0, cnt, n);
	}
   // printTable();
}

public static void main(String args[]){
	int keys[] = {200,300,1};
    int n = keys.length;
    cuckoo(keys,n);
}
}