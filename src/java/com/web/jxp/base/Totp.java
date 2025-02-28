package com.web.jxp.base;
import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import static dev.samstevens.totp.util.Utils.getDataUriForImage;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.tomcat.util.codec.binary.Base64;
public class Totp 
{
    public void saveImage(String datastr, String filepath, String filename)
    {
        try
        {
            String extstr = datastr.split(",")[0];
            String ext = extstr.substring(extstr.indexOf("/")+1, extstr.indexOf(";"));
            String base64Image = datastr.split(",")[1];
            byte[] data = Base64.decodeBase64(base64Image);
            try( OutputStream stream = new FileOutputStream(filepath+filename+"."+ext))
            {
               stream.write(data);
            }
            catch (Exception e) 
            {
               e.printStackTrace();
            }
        }
        catch (Exception e) 
        {
           e.printStackTrace();
        }
    }
    
    public String[] getimg()
    {
        String dataUri = "", secret = "";
        String arr[] = new String[2];
        try 
        {
            SecretGenerator secretGenerator = new DefaultSecretGenerator();
            secret = secretGenerator.generate();
            
            QrData data = new QrData.Builder().label("GOLDWOODMIS").secret(secret).issuer("APP")
                            .algorithm(HashingAlgorithm.SHA1)
                            .digits(6).period(30).build();
            QrGenerator generator = new ZxingPngQrGenerator();

            byte[] imageData = generator.generate(data);
            String mimeType = generator.getImageMimeType();
            dataUri = getDataUriForImage(imageData, mimeType);
            saveImage(dataUri, "D:\\santosh\\", "jxp");
        } 
        catch (QrGenerationException e) 
        {
            e.printStackTrace();
        }
        arr[0] = secret;
        arr[1] = dataUri;
        return arr;
    }   
    
    public boolean validate(String secret, String code)
    {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

        // secret = the shared secret for the user
        // code = the code submitted by the user
        boolean successful = verifier.isValidCode(secret, code);
        return successful;
    }
}
