package egovframework.com.cmm.util;

import java.io.Closeable;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Wrapper;

/**
 * Utility class  to support to close resources
 *
 * @author Vincent Han
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2014.09.18	표준프레임워크센터	최초 생성
 *
 * </pre>
 * @since 2014.09.18
 */
public class EgovResourceCloseHelper {
    /**
     * Resource close 처리.
     *
     * @param resources
     */
    public static void close(Closeable... resources) {
        for (Closeable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception ignore) {
                    EgovBasicLogger.ignore("Occurred Exception to close resource is ingored!!");
                }
            }
        }
    }

    /**
     * JDBC 관련 resource 객체 close 처리
     *
     * @param objects
     */
    public static void closeDBObjects(Wrapper... objects) {
        for (Object object : objects) {
            if (object != null) {
                if (object instanceof ResultSet) {
                    try {
                        ((ResultSet) object).close();
                    } catch (Exception ignore) {
                        EgovBasicLogger.ignore("Occurred Exception to close resource is ingored!!");
                    }
                } else if (object instanceof Statement) {
                    try {
                        ((Statement) object).close();
                    } catch (Exception ignore) {
                        EgovBasicLogger.ignore("Occurred Exception to close resource is ingored!!");
                    }
                } else if (object instanceof Connection) {
                    try {
                        ((Connection) object).close();
                    } catch (Exception ignore) {
                        EgovBasicLogger.ignore("Occurred Exception to close resource is ingored!!");
                    }
                } else {
                    throw new IllegalArgumentException("Wrapper type is not found : " + object.toString());
                }
            }
        }
    }

    /**
     * Socket 관련 resource 객체 close 처리
     *
     * @param objects
     */
    public static void closeSocketObjects(Socket socket, ServerSocket server) {
        if (socket != null) {
            try {
                socket.shutdownOutput();
            } catch (Exception ignore) {
                EgovBasicLogger.ignore("Occurred Exception to shutdown ouput is ignored!!");
            }

            try {
                socket.close();
            } catch (Exception ignore) {
                EgovBasicLogger.ignore("Occurred Exception to close resource is ignored!!");
            }
        }

        if (server != null) {
            try {
                server.close();
            } catch (Exception ignore) {
                EgovBasicLogger.ignore("Occurred Exception to close resource is ignored!!");
            }
        }
    }

    /**
     * Socket 관련 resource 객체 close 처리
     *
     * @param sockets
     */
    public static void closeSockets(Socket... sockets) {
        for (Socket socket : sockets) {
            if (socket != null) {
                try {
                    socket.shutdownOutput();
                } catch (Exception ignore) {
                    EgovBasicLogger.ignore("Occurred Exception to shutdown ouput is ignored!!");
                }

                try {
                    socket.close();
                } catch (Exception ignore) {
                    EgovBasicLogger.ignore("Occurred Exception to close resource is ignored!!");
                }
            }
        }
    }
}