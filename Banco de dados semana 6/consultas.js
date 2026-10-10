// Executar no mongosh
use ecommerce

// ========================================
// 1. COLLECTION CLIENTES
// ========================================

db.clientes.insertMany([
    {
        _id: 1,
        nome: "Ana Lima",
        email: "ana@example.com",
        cidade: "São Luís",
        ativo: true
    },
    {
        _id: 2,
        nome: "Bruno Souza",
        email: "bruno@example.com",
        cidade: "Imperatriz",
        ativo: true
    },
    {
        _id: 3,
        nome: "Carla Mendes",
        email: "carla@example.com",
        cidade: "São Luís",
        ativo: false
    },
    {
        _id: 4,
        nome: "Diego Costa",
        email: "diego@example.com",
        cidade: "Teresina",
        ativo: true
    },
    {
        _id: 5,
        nome: "Eva Rocha",
        email: "eva@example.com",
        cidade: "Belém",
        ativo: true
    }
]);

// ========================================
// 2. COLLECTION PRODUTOS
// ========================================

db.produtos.insertMany([
    {
        _id: 501,
        nome: "Teclado Mecânico",
        categoria: "Periféricos",
        preco_atual: 199.90,
        estoque: 20
    },
    {
        _id: 502,
        nome: "Mouse Gamer",
        categoria: "Periféricos",
        preco_atual: 89.90,
        estoque: 35
    },
    {
        _id: 503,
        nome: "Monitor 24 polegadas",
        categoria: "Monitores",
        preco_atual: 799.00,
        estoque: 8
    },
    {
        _id: 504,
        nome: "Webcam HD",
        categoria: "Acessórios",
        preco_atual: 159.90,
        estoque: 12
    },
    {
        _id: 505,
        nome: "Headset USB",
        categoria: "Áudio",
        preco_atual: 129.90,
        estoque: 17
    }
]);

// ========================================
// 3. COLLECTION PEDIDOS
// ========================================

// EMBEDDING:
// O endereço e os itens ficam dentro do pedido.
// Assim, podem ser consultados junto com ele.

db.pedidos.insertMany([
    {
        _id: 1001,
        cliente_id: 1,
        data: ISODate("2026-10-01T13:00:00Z"),
        status: "PAGO",

        endereco_entrega: {
            rua: "Rua das Flores",
            numero: "10",
            cidade: "São Luís",
            estado: "MA",
            cep: "65000000"
        },

        itens: [
            {
                produto_id: 501,
                nome: "Teclado Mecânico",
                quantidade: 1,
                preco_unitario: 199.90
            },
            {
                produto_id: 502,
                nome: "Mouse Gamer",
                quantidade: 2,
                preco_unitario: 89.90
            }
        ],

        total: 379.70
    },

    {
        _id: 1002,
        cliente_id: 2,
        data: ISODate("2026-10-02T15:30:00Z"),
        status: "ENVIADO",

        endereco_entrega: {
            rua: "Av. Central",
            numero: "200",
            cidade: "Imperatriz",
            estado: "MA",
            cep: "65900000"
        },

        itens: [
            {
                produto_id: 503,
                nome: "Monitor 24 polegadas",
                quantidade: 1,
                preco_unitario: 799.00
            }
        ],

        total: 799.00
    },

    {
        _id: 1003,
        cliente_id: 1,
        data: ISODate("2026-10-04T10:15:00Z"),
        status: "PENDENTE",

        endereco_entrega: {
            rua: "Rua das Flores",
            numero: "10",
            cidade: "São Luís",
            estado: "MA",
            cep: "65000000"
        },

        itens: [
            {
                produto_id: 504,
                nome: "Webcam HD",
                quantidade: 1,
                preco_unitario: 159.90
            }
        ],

        total: 159.90
    },

    {
        _id: 1004,
        cliente_id: 4,
        data: ISODate("2026-10-05T18:00:00Z"),
        status: "ENTREGUE",

        endereco_entrega: {
            rua: "Rua do Sol",
            numero: "45",
            cidade: "Teresina",
            estado: "PI",
            cep: "64000000"
        },

        itens: [
            {
                produto_id: 502,
                nome: "Mouse Gamer",
                quantidade: 1,
                preco_unitario: 89.90
            }
        ],

        total: 89.90
    },

    {
        _id: 1005,
        cliente_id: 5,
        data: ISODate("2026-10-06T12:00:00Z"),
        status: "CANCELADO",

        endereco_entrega: {
            rua: "Travessa Norte",
            numero: "7",
            cidade: "Belém",
            estado: "PA",
            cep: "66000000"
        },

        itens: [
            {
                produto_id: 501,
                nome: "Teclado Mecânico",
                quantidade: 1,
                preco_unitario: 199.90
            }
        ],

        total: 199.90
    }
]);

// ========================================
// 4. CONSULTAS COM FILTRO
// ========================================

// Pedidos com status PAGO
db.pedidos.find({
    status: "PAGO"
});

// Produtos com preço menor que 200
db.produtos.find({
    preco_atual: {
        $lt: 200
    }
});

// Clientes ativos
db.clientes.find({
    ativo: true
});

// ========================================
// 5. ORDENAÇÃO E PROJEÇÃO
// ========================================

// Produtos em ordem crescente de preço.
// Retorna somente nome, preço e estoque.

db.produtos.find(
    {
        preco_atual: {
            $lt: 200
        },
        estoque: {
            $gt: 0
        }
    },
    {
        _id: 0,
        nome: 1,
        preco_atual: 1,
        estoque: 1
    }
).sort({
    preco_atual: 1
});

// Pedidos pagos, do maior para o menor valor.

db.pedidos.find(
    {
        status: "PAGO"
    },
    {
        _id: 1,
        cliente_id: 1,
        status: 1,
        total: 1
    }
).sort({
    total: -1
});

// ========================================
// 6. ÍNDICES
// ========================================

// Índice composto para buscar pedidos por cliente
// e ordenar pela data mais recente.

db.pedidos.createIndex({
    cliente_id: 1,
    data: -1
});

// Índice de texto para pesquisar produtos.

db.produtos.createIndex({
    nome: "text",
    categoria: "text"
});

// Consulta usando o índice de texto.

db.produtos.find({
    $text: {
        $search: "mouse"
    }
});

// ========================================
// 7. ANÁLISE COM EXPLAIN()
// ========================================

// Analisa a busca de pedidos por cliente.

db.pedidos.find({
    cliente_id: 1
}).sort({
    data: -1
}).explain("executionStats");

// Analisa a busca textual de produtos.

db.produtos.find({
    $text: {
        $search: "mouse"
    }
}).explain("executionStats");

// ========================================
// 8. CONTAGEM DE DOCUMENTOS
// ========================================

db.clientes.countDocuments();

db.produtos.countDocuments();

db.pedidos.countDocuments();
