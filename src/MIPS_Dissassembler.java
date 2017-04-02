/*/
 * @author Don Charest
 * Project 1: MIPS Dissassembler
 * Due: 10/6/16
 */

public class MIPS_Dissassembler
{
	// Given Machine Instructions (can be edited as necessary)
	private static int[] machineInstructions = { 0x022DA822, 0x8EF30018, 0x12A70004, 0x02689820, 0xAD930018, 0x02687824,
			0xAD8FFFF4, 0x018C6020, 0x02A4A825, 0x158FFFF6, 0x8E59FFF0 };

	private static String opCode, rs, rt, rd, function, offset;
	private static int rsDecimal, rtDecimal, rdDecimal, offsetDecimal;
	private static int startingAddress = 0x7A060 - 0x00004;
	private static int currentAddress, offsetAddress, branchedAddress;

	public static void main(String[] args)
	{
		// Start loop to cycle through Machine Instructions Array
		for (int i = 0; i < machineInstructions.length; i++)
		{
			String hexInstruction = Integer.toHexString(machineInstructions[i]);
			String binaryInstruction = Integer.toBinaryString(machineInstructions[i]);

			// Add leading zeros to hexadecimal values with less than 32-bits (8
			// digits)
			for (int j = hexInstruction.length(); j != 8; j++)
			{
				hexInstruction = "0" + hexInstruction;
			}
			// Add leading zeros to binary values with less than 32-bits (32
			// digits)
			for (int k = binaryInstruction.length(); k != 32; k++)
			{
				binaryInstruction = "0" + binaryInstruction;
			}

			// Index address by 4 bytes (start at minus 4 so initial is correct)
			currentAddress = startingAddress += 0x04;

			// R-Type & I-Type Instruction format fields
			opCode = binaryInstruction.substring(0, 6);
			rs = binaryInstruction.substring(6, 11);
			rt = binaryInstruction.substring(11, 16);
			rd = binaryInstruction.substring(16, 21);
			function = binaryInstruction.substring(26, 32);
			offset = binaryInstruction.substring(16, 32);

			// (short) will handle negative decimal numbers
			offsetDecimal = (short) Integer.parseInt(offset, 2);

			// Shift over 2places to convert from 16 to 18
			offsetAddress = (short) offsetDecimal << 2;

			// Decode branched address (includes 4bytes from PC)
			branchedAddress = currentAddress + offsetAddress + 0x04;

			// Register Fields converted to base10
			rsDecimal = Integer.parseInt(rs, 2);
			rtDecimal = Integer.parseInt(rt, 2);
			rdDecimal = Integer.parseInt(rd, 2);

			switch (opCode)
			{
			case "000000": // R-Type Instruction Format

				switch (function)
				{
				case "100000": // "Add" instruction
					System.out.println("0x" + Integer.toHexString(currentAddress) + "\tadd $" + rdDecimal + ", $"
							+ rsDecimal + ", $" + rtDecimal);
					System.out.print("\n");
					break;

				case "100010": // "Subtract" instruction
					System.out.println("0x" + Integer.toHexString(currentAddress) + "\tsub $" + rdDecimal + ", $"
							+ rsDecimal + ", $" + rtDecimal);
					System.out.print("\n");
					break;

				case "100100": // "and" instruction
					System.out.println("0x" + Integer.toHexString(currentAddress) + "\tand $" + rdDecimal + ", $"
							+ rsDecimal + ", $" + rtDecimal);
					System.out.print("\n");
					break;

				case "100101": // "or" instruction
					System.out.println("0x" + Integer.toHexString(currentAddress) + "\tor $" + rdDecimal + ", $"
							+ rsDecimal + ", $" + rtDecimal);
					System.out.print("\n");
					break;

				case "101010": // "Subtract" instruction
					System.out.println("0x" + Integer.toHexString(currentAddress) + "\tslt $" + rdDecimal + ", $"
							+ rsDecimal + ", $" + rtDecimal);
					System.out.print("\n");
					break;

				default:
					// Message to alert the function code provided has not been
					// programmed
					System.out.println("function code not supported");
					System.out.print("\n");
					break;
					
				} // End switch(function)

			case "100011": // I-Type Instruction Format, Load Word (lw)
				System.out.println("0x" + Integer.toHexString(currentAddress) + "\tlw $" + rtDecimal + ", "
						+ offsetDecimal + "($" + rsDecimal + ")");
				System.out.print("\n");
				break;

			case "101011": // I-Type Instruction Format, Store Word (sw)
				System.out.println("0x" + Integer.toHexString(currentAddress) + "\tsw $" + rtDecimal + ", "
						+ offsetDecimal + "($" + rsDecimal + ")");
				System.out.print("\n");
				break;

			case "000100": // I-Type Instruction Format, Branch if Equal (beq)
				System.out.println("0x" + Integer.toHexString(currentAddress) + "\tbeq $" + rsDecimal + ", $"
						+ rtDecimal + ", address 0x" + Integer.toHexString(branchedAddress));
				System.out.print("\n");
				break;

			case "000101": // I-Type Instruction, Branch if Not Equal (bne)
				System.out.println("0x" + Integer.toHexString(currentAddress) + "\tbne $" + rsDecimal + ", $"
						+ rtDecimal + ", address 0x" + Integer.toHexString(branchedAddress));
				System.out.print("\n");
				break;

			} // End switch(opCode)

		} // End for loop

	} // End Main

} // End Class
