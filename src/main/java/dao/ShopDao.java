package dao;
import bkacad.Products;
import Connection.ConnectionBKACAD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShopDao {
    public List<Products> getAll() {
        List<Products> productsList = new ArrayList<>();
        try {
            Connection conn = ConnectionBKACAD.getConnection();
            final String sql = "SELECT * FROM products";
            Statement stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery(sql);
            while (rs.next()){
                Products p = new Products();
                p.setId(rs.getInt("id"));
                p.setProduct_name(rs.getString("product_name"));
                p.setProduct_price(rs.getInt("product_price"));
                p.setProduct_size(rs.getString("product_size"));
                p.setProduct_color(rs.getString("product_color"));
                p.setBrand_id(rs.getInt("brand_id"));
                productsList.add(p);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception p) {
            p.printStackTrace();
        }
        return productsList;
    }
    public static void insert(Products p){
        final String sql = String.format("INSERT INTO products VALUES ('%s', '%d', '%s', '%s', '%d')",
                p.getProduct_name(),p.getProduct_price(),p.getProduct_price(),p.getProduct_size(),
                p.getProduct_color(),p.getBrand_id());
        try {
            Connection conn= ConnectionBKACAD.getConnection();
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(sql);
            if(rs ==0){
                System.out.println("Error");
            }
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Products getById(long id) {
        final String sql = "SELECT * FROM `products` WHERE  `id` = " + id;
        Products p = null;

        try {
            Connection conn = ConnectionBKACAD.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                p = new Products();
                p.setId(rs.getInt("id"));
                p.setProduct_name(rs.getString("product_name"));
                p.setProduct_color(rs.getString("product_color"));
                p.setProduct_size(rs.getString("product_size"));
                p.setBrand_id(rs.getInt("brand_id"));
                p.setProduct_price(rs.getInt("product_price"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
    public void update(Products product, long id) {
        Products tmp = getById(id);
        if (tmp == null) {
            throw new RuntimeException("Sản phẩm không tồn tại!");
        }

        final String sql = String.format("UPDATE `products` SET `product_name`='%s',`product_price`='%d',`product_size`='%s',`product_color`='%s',`brand_id`='%d' WHERE `id` = '%d'",
                product.getProduct_name(), product.getProduct_price(), product.getProduct_size(), product.getProduct_color(), product.getBrand_id(), id
        );
        try {
            Connection conn = ConnectionBKACAD.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Cập nhật thất bại");
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void delete(int id){
        try {
            Connection conn = ConnectionBKACAD.getConnection();
            final String sql = "DELETE FROM products WHERE id = " +id;
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);
            if (rs == 0){
                System.out.println("Delete fail");
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
