package com.smartdatasolutions.test.impl;

import com.smartdatasolutions.test.Member;
import com.smartdatasolutions.test.MemberExporter;
import com.smartdatasolutions.test.MemberFileConverter;
import com.smartdatasolutions.test.MemberImporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main extends MemberFileConverter {

	@Override
	protected MemberExporter getMemberExporter( ) {
		// TODO
		MemberExporterImpl m_exp =new MemberExporterImpl();
		return m_exp;
	}

	@Override
	protected  MemberImporter getMemberImporter( ) {
		// TODO
		MemberImporterImpl m_imp = new MemberImporterImpl();
		return m_imp;
	}

	@Override
	protected List< Member > getNonDuplicateMembers( List< Member > membersFromFile ) {

		// TODO
		List<Member> validMembers  = new ArrayList<Member>();
		for (Member member:membersFromFile) {
			if(!validMembers.contains(member)){
				validMembers.add(member);
			}

		}
		return validMembers;
	}

	@Override
	protected Map< String, List< Member >> splitMembersByState( List< Member > validMembers ) {
		// TODO
		Map<String, List<Member>> members_by_state  =new HashMap<String, List<Member>>();
		for (Member member: validMembers) {
			List<Member> temp = members_by_state.get(member.getState());

			if(temp == null){
				// if the list is null we haven't seen an
				// object with this id before, so create
				// a new list
				temp = new ArrayList<Member>();

				// and add it to the map
				members_by_state.put(member.getState(), temp);
			}

			// whether we got the list from the map
			// or made a new one we need to add our
			// object.
			temp.add(member);

		}
		 return members_by_state;



	}
	public void convert(File inputMemberFile, String outputFilePath, String outputFileName) throws Exception {

		/*
		 * Read :
		 */
		MemberImporter memberImporter = getMemberImporter();
		List<Member> membersFromFile = memberImporter.importMembers(inputMemberFile);


		/*
		 * Filter :
		 */

		List<Member> validMembers = getNonDuplicateMembers(membersFromFile);
		Map<String, List<Member>> membersFilteredByState = splitMembersByState(validMembers);

		/*
		 * Write :
		 */
		for (Map.Entry<String, List<Member>> map : membersFilteredByState.entrySet()) {


			String state = map.getKey();
			List<Member> membersPerState = map.getValue();

			File outputFile = new File(outputFilePath + File.separator + state + "_" + outputFileName);

			MemberExporter exporter = getMemberExporter();
			writeMembers(outputFile, exporter, membersPerState);

		}
	}
	private void writeMembers( File outputFile, MemberExporter exporter, List< Member > members ) throws IOException {
		Writer writer = new FileWriter( outputFile ,true);

		for ( Member member: members ) {
			exporter.writeMember( member, writer );
		}
		System.out.println("Files Have been Written Successfully to :" + outputFile.getName());
		writer.flush( );
		writer.close( );
	}
	public static void main(String[] args){

		Main main = new Main();

			List<Member> members;
			File file = new File("C:\\Users\\atman.rauniar\\Downloads\\SDS_Entry_Maven\\SDS_Entry_Maven\\Members.txt");

		try {
			main.convert(file, "C:\\Users\\atman.rauniar\\Downloads\\SDS_Entry_Maven\\", "outputFile.csv");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


	}

}
