package game;

import java.io.*;

/**
 * Created by majkl on 05.05.2016.
 */

public class Save implements java.io.Serializable{

        public String fileName;


        public Save(String fileName)
        {
            this.fileName=fileName;
        }

        public void saveFile(Serializable game)
        {
            try
            {
                FileOutputStream f = new FileOutputStream(this.fileName);
                ObjectOutputStream obj= new ObjectOutputStream(f);
                obj.writeObject(game);
                f.close();
            }
            catch (IOException ex )
            {
                ex.printStackTrace();
            }
        }
        public Object loadFile() { //throws IOException, ClassNotFoundException {

            if (checkFile()){
                try
                {
                    FileInputStream f = new FileInputStream(this.fileName);
                    ObjectInputStream obj = new ObjectInputStream(f);
                    Game loadedGame = (Game)obj.readObject();
                    f.close();
                    return loadedGame;
                }
                catch (IOException | ClassNotFoundException ex)
                {
                    ex.printStackTrace();
                }
            }
            return null;
        }
        public boolean checkFile()
        {
            return new File(this.fileName).isFile();
        }

        public void deleteSave()
        {
            try{

                File file = new File(System.getProperty("user.dir")+"\\"+this.fileName);

                System.out.println(file.toString());

                if(file.delete()){
                    System.out.println(file.getName() + " is deleted!");
                }else{
                    System.out.println("Delete operation failed.");
                }

            }catch(Exception e){

                e.printStackTrace();
            }
        }

}
