package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);

	/* Your code goes here */
        double [] freq = new double [128];
        ArrayList<CharFreq> list = new ArrayList<>();
        int length = 0;
    
        while(StdIn.hasNextChar()){
            char letter = StdIn.readChar();
            freq[letter] += 1.00;
            length++;
        }
        for(int i = 0; i < freq.length; i++){
            if(freq[i] > 0.0){
                CharFreq charFreq = new CharFreq((char) i, freq[i]/length);
                list.add(charFreq);
            }
        }
        if (list.size() == 1){
            int num =  list.get(0).getCharacter();
            if(num == 127){
                CharFreq empty = new CharFreq((char) 0, 0.00);
                list.add(empty);
            } else {
                CharFreq empty = new CharFreq((char) (num+1), 0.00);
                list.add(empty);
            }
        }
        Collections.sort(list);
        sortedCharFreqList = list;
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {

	/* Your code goes here */
        Queue<TreeNode> source = new Queue<>();
        Queue<TreeNode> target = new Queue<>();
        TreeNode left = new TreeNode();
        TreeNode right = new TreeNode();

        for(int i = 0; i < sortedCharFreqList.size(); i++){
            TreeNode tmp = new TreeNode(sortedCharFreqList.get(i), null, null);
            source.enqueue(tmp);
        }

        while(source.isEmpty() != true || target.size() != 1 ){
            if(target.isEmpty() == true){
                left = source.dequeue();
                right = source.dequeue();
                
                CharFreq total = new CharFreq(null, left.getData().getProbOcc() + right.getData().getProbOcc());
                TreeNode sumNode = new TreeNode(total, left, right);
                target.enqueue(sumNode);
            }else{
                if(source.isEmpty() == false){
                    left = source.peek();
                } else {
                    left = target.peek();
                }
                if(target.isEmpty() == false){
                    if(left.getData().getProbOcc() <= target.peek().getData().getProbOcc() && source.isEmpty() == false){
                        left = source.dequeue();
                    } else {
                        left = target.dequeue();
                    }
                } else {
                    left = source.dequeue();
                }

                if(source.isEmpty() == false){
                    right = source.peek();
                } else {
                    right = target.peek();
                }
                if(target.isEmpty() == false){
                    if(right.getData().getProbOcc() <= target.peek().getData().getProbOcc() && source.isEmpty() == false){
                        right = source.dequeue();
                    } else {
                        right = target.dequeue();
                    }
                } else {
                    right = source.dequeue();
                }

                CharFreq total = new CharFreq(null, left.getData().getProbOcc() + right.getData().getProbOcc());
                TreeNode sumNode = new TreeNode(total, left, right);
                target.enqueue(sumNode);
            }
        }
            
        huffmanRoot = target.peek();
    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */

     
    public void makeEncodings() {

	/* Your code goes here */
        String[] tempEncode = new String[128];
        String encode = "";

        inorder(huffmanRoot, encode, tempEncode);

        encodings = tempEncode;
        
    }
    private void inorder(TreeNode ptr, String encode, String[] tempEncode){
        if(ptr == null){
            return;
        }
        inorder(ptr.getLeft(), encode + 0, tempEncode);
        if(ptr.getData().getCharacter() != null){
            int index = (int) ptr.getData().getCharacter();
            tempEncode[index] = encode;  
        }
        inorder(ptr.getRight(), encode + 1, tempEncode);

    }
    

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);

	/* Your code goes here */
        String stringBit = ""; 
        char ch;

        while(StdIn.hasNextChar() == true){
            ch = StdIn.readChar();
            stringBit = stringBit + encodings[(int) ch];
        }
        writeBitString(encodedFile, stringBit);
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

	/* Your code goes here */
        char[] binary = readBitString(encodedFile).toCharArray();
        TreeNode ptr = huffmanRoot;
        for(int i = 0; i < binary.length; i++){
            char pathway = binary[i];
            if(pathway == '1'){
                ptr = ptr.getRight();
                
            } else {
                ptr = ptr.getLeft();
            }
            if(ptr.getLeft() == null){
                StdOut.print(ptr.getData().getCharacter());
                ptr = huffmanRoot;
            }
        }
    }
    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
