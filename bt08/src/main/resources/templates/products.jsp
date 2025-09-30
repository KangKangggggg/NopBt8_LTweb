<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products Management</title>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>
    <h1>Quản lý Sản phẩm - GraphQL</h1>
    
    <div>
        <h2>Danh sách sản phẩm theo giá</h2>
        <button onclick="loadProductsByPrice()">Tải sản phẩm</button>
        <div id="productsContainer"></div>
    </div>
    
    <script>
        const GRAPHQL_URL = 'http://localhost:8080/graphql';
        
        async function graphqlQuery(query, variables = {}) {
            try {
                const response = await axios.post(GRAPHQL_URL, {
                    query: query,
                    variables: variables
                });
                return response.data;
            } catch (error) {
                console.error('GraphQL Error:', error);
                return { errors: [error.message] };
            }
        }
        
        async function loadProductsByPrice() {
            const query = `
                query {
                    productsOrderByPrice {
                        id
                        title
                        price
                        quantity
                        description
                        user {
                            fullname
                            email
                        }
                    }
                }
            `;
            
            const result = await graphqlQuery(query);
            
            if (result.data && result.data.productsOrderByPrice) {
                const products = result.data.productsOrderByPrice;
                let html = '<table border="1" style="width:100%">';
                html += '<tr><th>ID</th><th>Title</th><th>Price</th><th>Quantity</th><th>Description</th><th>User</th></tr>';
                
                products.forEach(product => {
                    html += `<tr>
                        <td>${product.id}</td>
                        <td>${product.title}</td>
                        <td>${product.price}</td>
                        <td>${product.quantity}</td>
                        <td>${product.description}</td>
                        <td>${product.user ? product.user.fullname : 'N/A'}</td>
                    </tr>`;
                });
                
                html += '</table>';
                document.getElementById('productsContainer').innerHTML = html;
            }
        }
    </script>
</body>
</html>