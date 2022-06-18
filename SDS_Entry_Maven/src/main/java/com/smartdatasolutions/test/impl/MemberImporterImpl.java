package com.smartdatasolutions.test.impl;

import com.smartdatasolutions.test.Member;
import com.smartdatasolutions.test.MemberImporter;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemberImporterImpl implements MemberImporter {

	@Override
	public List< Member > importMembers( File inputFile ) throws Exception {
		/*
		 * Implement the missing logic
		 */
		List<Member> membersList = new ArrayList<Member>();

		try (BufferedReader br = new BufferedReader( new FileReader( inputFile ) )) {
			String line = "";
			Member tmp;
			System.out.println("Extracting Member details from the File.....");
			while ( br.readLine( )!=null ) {
				line = br.readLine( );
				if (line != null){
					String[] lineItems = line.split("\\s\\s+"); //Splitting one or more white spaces
					String[] lineItemsFix = Arrays.copyOf(lineItems, lineItems.length + 2);
					lineItems = lineItemsFix;
					String zip = "";
					zip = line.replaceAll("[^0-9]+", " ");
					lineItemsFix[6] = line.split(" +")[8];

					//create a tmp member object and set its properties
					tmp = new Member();
					tmp.setId(lineItems[0]);
					tmp.setFirstName(lineItems[2]);
					tmp.setLastName(lineItems[1]);
					tmp.setAddress(lineItems[3]);
					if (lineItems[4].length() > 20){tmp.setCity(lineItems[4].substring(0,20));}
					else{tmp.setCity(lineItems[4]);}
					if(lineItems[5].split("  ")[0].length()>4){tmp.setState(lineItems[4].substring(20,22));}
					else{tmp.setState(lineItems[5].split("  ")[0]);}
					tmp.setZip(Arrays.asList(zip.trim().split(" ")).get(2));


					membersList.add(tmp);

				}

			}

		}
		System.out.println("Extracted Member details from the File.....Now Writing Data in CSV");
		return membersList;
	}
	
	

}
