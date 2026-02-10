import http from 'k6/http';
import { group, sleep, check } from 'k6';

export let options = {
  scenarios: {
    smoke: {
      executor: 'constant-vus',
      vus: 3,
      duration: '20s',
      tags: { test_type: 'smoke' },
    },

    load: {
      executor: 'constant-vus',
      vus: 25,
      duration: '40s',
      tags: { test_type: 'load' },
    },
  },

  thresholds: {
    http_req_duration: ['p(95)<2000'], // 95% of requests < 2s
    http_req_failed: ['rate<0.01'],    // <1% failures
  },
};

const BASE_URL = 'https://dummyjson.com';

export default function () {

  group('Get Products', () => {
    const res = http.get(`${BASE_URL}/products`);

    check(res, {
      'status is 200': (r) => r.status === 200,
      'products returned': (r) => r.json('products') !== undefined,
    });
  });

  group('Add Product', () => {
    const payload = JSON.stringify({
      title: 'Mai Test Product',
      price: 100,
      description: 'Added Product For Performance Testing',
      category: 'laptops',
    });

    const headers = {
      'Content-Type': 'application/json',
    };

    const res = http.post(`${BASE_URL}/products/add`, payload, { headers });

    check(res, {
      'product created (201)': (r) => r.status === 201,
      'product id exists': (r) => r.json('id') !== undefined,
    });
  });

  group('Delete Product', () => {
    const res = http.del(`${BASE_URL}/products/1`);

    check(res, {
      'delete success': (r) => r.status === 200,
    });
  });

  sleep(1);
}

