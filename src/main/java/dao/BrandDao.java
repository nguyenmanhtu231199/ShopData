package dao;

import Connection.ConnectionBKACAD;
import bkacad.Brands;
import bkacad.Products;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BrandDao {
    public static List<Brands> getAll() {
        final String sql = "SELECT * FROM `brands`";

        List<Brands> brandList = new ArrayList<>();

        try {
            Connection conn = ConnectionBKACAD.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Brands b = new Brands();
                b.setId(rs.getInt("id"));
                b.setBrand_name(rs.getString("brand_name"));
                b.setBrand_address(rs.getString("brand_address"));
                brandList.add(b);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return brandList;
    }

    public Brands getById(long id) {
        final String sql = "SELECT * FROM `brands` WHERE  `id` = " + id;
        Brands b = null;

        try {
            Connection conn = ConnectionBKACAD.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                b = new Brands();
                b.setId(rs.getInt("id"));
                b.setBrand_name(rs.getString("brand_name"));
                b.setBrand_address(rs.getString("brand_address"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public List<Products> getAllProductByBrand(long brandID) {
        Brands b = getById(brandID);
        if (b == null) {
            throw new RuntimeException("Hãng không tồn tại!");
        }
        // SQL
        final String sql = "SELECT * FROM `products` WHERE `brand_id` = " + brandID;

        List<Products> productList = new ArrayList<>();
        try {
            Connection conn = ConnectionBKACAD.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Products p = new Products();
                p.setId(rs.getInt("id"));
                p.setProduct_name(rs.getString("product_name"));
                p.setProduct_color(rs.getString("product_color"));
                p.setProduct_size(rs.getString("product_size"));
                p.setBrand_id(rs.getInt("brand_id"));
                p.setProduct_price(rs.getInt("product_price"));
                productList.add(p);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

    public void insert(Brands brand) {
        final String sql = String.format("INSERT INTO `brands` VALUES (NULL,'%s','%s')",
                brand.getBrand_name(), brand.getBrand_address());
        try {
            Connection conn = ConnectionBKACAD.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Thêm thất bại");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Brands brand, long id) {
        Brands b = getById(id);
        if (b == null) {
            throw new RuntimeException("Hãng không tồn tại!");
        }

        final String sql = String.format(
                "UPDATE `brands` SET `brand_name`='%s', `brand_address`='%s' WHERE `id` = '%d'",
                brand.getBrand_name(), brand.getBrand_address(), id
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

    public void delete(long id) {
        final String sql = "DELETE FROM `brands` WHERE `id` = " + id;
        try {
            Connection conn = ConnectionBKACAD.getConnection();
            Statement stmt = conn.createStatement();
            long rs = stmt.executeUpdate(sql);

            if (rs == 0) {
                System.out.println("Xoá thất bại");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
