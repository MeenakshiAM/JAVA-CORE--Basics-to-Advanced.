public class 

FileWriter fw = new FileWriter("users.txt", true); // true = append
BufferedWriter bw = new BufferedWriter(fw);

bw.write("1,Meena,22");
bw.newLine();

bw.close();