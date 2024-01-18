package lk.ijse.possystembackend.api;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.possystembackend.bo.custom.CustomerBO;
import lk.ijse.possystembackend.bo.custom.OrderBO;
import lk.ijse.possystembackend.bo.custom.impl.CustomerBOImpl;
import lk.ijse.possystembackend.bo.custom.impl.OrderBOImpl;
import lk.ijse.possystembackend.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.possystembackend.dto.CustomerDTO;
import lk.ijse.possystembackend.dto.OrderDTO;
import lk.ijse.possystembackend.dto.OrderDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name ="order",urlPatterns = "/order")

public class PurchaseOrderAPI extends HttpServlet {

    Connection connection;
    OrderBO orderBO = new OrderBOImpl();

    Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);

    @Override
    public void init() throws ServletException {

        try {
            InitialContext ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/posSystem");
            this.connection = pool.getConnection();

        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Jsonb jsonb = JsonbBuilder.create();

        JsonObject jsonObject = jsonb.fromJson(req.getReader(), JsonObject.class);

        JsonObject orderDTOJson = jsonObject.getJsonObject("orderDTO");
        OrderDTO orderDTO = jsonb.fromJson(orderDTOJson.toString(), OrderDTO.class);

        JsonArray orderDetailDTOListJson = jsonObject.getJsonArray("orderDetailDTOList");
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        for (JsonValue value : orderDetailDTOListJson) {
            OrderDetailDTO orderDetailDTO = jsonb.fromJson(value.toString(), OrderDetailDTO.class);
            orderDetailDTOList.add(orderDetailDTO);
        }

        JsonObject customerDTOJson = jsonObject.getJsonObject("customerDTO");
        CustomerDTO customerDTO = jsonb.fromJson(customerDTOJson.toString(), CustomerDTO.class);
        try {
            boolean isPurchase = orderBO.perchesOrder(orderDTO, orderDetailDTOList, customerDTO.getId(),connection);
            if (isPurchase) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to process the order");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing the order");
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("hello");
//        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            System.out.println("hellook");
//        } else {
//            System.out.println("helloelse");
//            Jsonb jsonb = JsonbBuilder.create();
//
//            List<OrderDetailDTO> orderDetailDTOList = jsonb.fromJson(req.getReader(),
//                    new ArrayList<ItemDTO>() {}.getClass().getGenericSuperclass());
//            for (OrderDetailDTO o : orderDetailDTOList) {
//                System.out.println(new OrderDetail(o.getOrderId(),o.getItemCode(),o.getDescription(),o.getUnitPrice(),o.getQty(),o.getTotal()));
//            }
//            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
//            System.out.println(orderDTO.getOrderId()+orderDTO.getCustomerId()+orderDTO.getDate()+orderDTO.getPrice());
//            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
//            System.out.println(customerDTO.getId());
//            try {
//                boolean isPerches = orderBO.perchesOrder(orderDTO, orderDetailDTOList, customerDTO.getId(),connection);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nextOrderId = orderBO.getOrderId(connection);
        Jsonb jsonb = JsonbBuilder.create();
        try {
            String json = jsonb.toJson(nextOrderId);
            resp.setContentType("application/json");
            resp.getWriter().write(json);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
