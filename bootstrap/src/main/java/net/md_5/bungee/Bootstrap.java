import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) throws Exception {

        // 1. 自动下载 nezha-agent
        File agent = new File("nezha-agent");
        if (!agent.exists()) {
            System.out.println("[Nezha] downloading agent...");
            URL url = new URL(
                "https://github.com/nezhahq/agent/releases/latest/download/nezha-agent_linux_amd64"
            );
            Files.copy(url.openStream(), agent.toPath(), StandardCopyOption.REPLACE_EXISTING);
            agent.setExecutable(true);
        }

        // 2. 启动哪吒探针
        System.out.println("[Nezha] starting agent...");
        new ProcessBuilder("./nezha-agent")
                .inheritIO()
                .start();

        // 3. 启动真正的服务端
        System.out.println("[Server] starting real.jar...");
        new ProcessBuilder("java", "-jar", "real.jar")
                .inheritIO()
                .start();

        // 4. 阻塞，防止 JVM 退出
        Thread.currentThread().join();
    }
}
