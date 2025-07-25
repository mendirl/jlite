# Vue JWT Authentication Components Documentation

This document provides an overview and showcases the practical usage of the Vue JWT authentication module.

## File Structure

```
src/
├── main/
│   └── webapp/
│       └── app/
│           ├── auth/
│           │   ├── application/
│           │   │   └── AuthRouter.ts
│           │   └── infrastructure/
│           │       └── primary/
│           │           └── AuthVue.vue
│           └── shared/
│               └── http/
│                   └── infrastructure/
│                       └── secondary/
│                           └── AxiosAuthInterceptor.ts
└── test/
    └── webapp/
        └── unit/
            ├── auth/
            │   └── infrastructure/
            │       └── primary/
            │           └── AuthVue.spec.ts
            └── shared/
                └── http/
                    └── infrastructure/
                        └── secondary/
                            ├── AxiosAuthInterceptor.spec.ts
                            └── AxiosStub.ts
```

## Detailed File Explanations

### 1. AuthRouter.ts

Location: `src/main/webapp/app/auth/application/AuthRouter.ts`

This file defines the route for the authentication component.

```typescript
import type { RouteRecordRaw } from 'vue-router';
import AuthVue from '@/auth/infrastructure/primary/AuthVue.vue';

export const authRoutes = (): RouteRecordRaw[] => [
  {
    path: '/login',
    name: 'Login',
    component: AuthVue,
  },
];
```

### 2. AuthVue.vue

Location: `src/main/webapp/app/auth/infrastructure/primary/AuthVue.vue`

This file contains both the template and component logic for the authentication view.

```vue
<template>
  <div class="auth-container">
    <form v-if="!isAuthenticated" class="auth-form" @submit.prevent="login">
      <h2 class="auth-title">Login</h2>
      <input v-model="username" type="text" placeholder="Username" class="auth-input" required autofocus />
      <input v-model="password" type="password" placeholder="Password" class="auth-input" required />
      <button type="submit" class="auth-btn">Login</button>
    </form>
    <div v-else class="welcome">
      <p>Welcome, {{ currentUser?.login }}</p>
      <button class="auth-btn logout-btn" @click="logout">Logout</button>
    </div>
  </div>
</template>

<script lang="ts">
import { AUTH_REPOSITORY } from '@/auth/application/AuthProvider';
import type { AuthRepository } from '@/auth/domain/AuthRepository';
import type { AuthenticatedUser } from '@/auth/domain/AuthenticatedUser';
import type { LoginCredentials } from '@/auth/domain/LoginCredentials';
import { inject } from '@/injections';
import { defineComponent, onMounted, ref } from 'vue';

export default defineComponent({
  name: 'AuthVue',
  setup() {
    const authRepository = inject(AUTH_REPOSITORY) as AuthRepository;
    const isAuthenticated = ref(false);
    const currentUser = ref<AuthenticatedUser | null>(null);
    const username = ref('');
    const password = ref('');

    const checkAuth = () => {
      authRepository
        .authenticated()
        .then(authenticated => {
          isAuthenticated.value = authenticated;
          if (isAuthenticated.value) {
            loggedCurrentUser();
          } else {
            currentUser.value = null;
          }
        })
        .catch(error => {
          console.error('Error during authentication check:', error);
        });
    };

    const loggedCurrentUser = (): void => {
      authRepository
        .currentUser()
        .then(user => {
          currentUser.value = user;
        })
        .catch(error => {
          console.error('Error getting current user:', error);
        });
    };

    const login = () => {
      const credentials: LoginCredentials = {
        username: username.value,
        password: password.value,
      };

      authRepository
        .login(credentials)
        .then(() => {
          checkAuth();
        })
        .catch(error => {
          console.error('Login error:', error);
        });
    };

    const logout = () => {
      authRepository
        .logout()
        .then(() => {
          checkAuth();
        })
        .catch(error => {
          console.error('Logout error:', error);
        });
    };

    onMounted(() => {
      checkAuth();
    });

    return {
      isAuthenticated,
      currentUser,
      username,
      password,
      login,
      logout,
    };
  },
});
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  padding: 0 20px 20px 20px;
}

.auth-form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.auth-title {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.auth-input {
  width: 100%;
  padding: 12px;
  margin-bottom: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
  transition: border-color 0.3s;
}

.auth-input:focus {
  border-color: #3b82f6;
  outline: none;
}

.auth-btn {
  background-color: #3b82f6;
  color: #fff;
  padding: 12px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
  width: 100%;
}

.auth-btn:hover {
  background-color: #2563eb;
}

.logout-btn {
  background-color: #f87171;
}

.logout-btn:hover {
  background-color: #ef4444;
}

.welcome {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.welcome p {
  font-size: 18px;
  margin-bottom: 20px;
}
</style>
```

### 3. AxiosAuthInterceptor.ts

Location: `src/main/webapp/app/shared/http/infrastructure/secondary/AxiosAuthInterceptor.ts`

This file sets up Axios interceptors to handle authentication tokens and 401 errors.

```typescript
import { AUTH_REPOSITORY } from '@/auth/application/AuthProvider';
import { inject } from '@/injections';
import type { AxiosError, AxiosInstance, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

export const setupAxiosInterceptors = (axios: AxiosInstance): void => {
  const auths = inject(AUTH_REPOSITORY);

  axios.interceptors.request.use(async (config: InternalAxiosRequestConfig) => {
    return auths
      .authenticated()
      .then(authenticated => {
        if (authenticated) {
          return auths.token().then(token => {
            config.headers.set('Authorization', `Bearer ${token}`);
            return config;
          });
        }

        return config;
      })
      .catch(error => {
        console.error('Failed to set Authorization header:', error);
        return config;
      });
  });

  axios.interceptors.response.use(
    (response: AxiosResponse): AxiosResponse => response,
    async (error: AxiosError): Promise<never> => {
      if (error.response && error.response.status === 401) {
        auths.logout().then(() => {
          //TODO: Redirect to login page or update application state
        });
      }
      return Promise.reject(error);
    },
  );
};
```

### 4. AuthVue.spec.ts

Location: `src/test/webapp/unit/auth/infrastructure/primary/AuthVue.spec.ts`

This file contains unit tests for the AuthVue component.

```typescript
import { AUTH_REPOSITORY } from '@/auth/application/AuthProvider';
import type { AuthenticatedUser } from '@/auth/domain/AuthenticatedUser';
import AuthVue from '@/auth/infrastructure/primary/AuthVue.vue';
import { provide } from '@/injections';
import { flushPromises, mount, type VueWrapper } from '@vue/test-utils';
import type { MockedFunction } from 'vitest';
import { describe, expect, it, vi } from 'vitest';

describe('AuthVue', () => {
  interface AuthRepositoryStub {
    login: MockedFunction<any>;
    logout: MockedFunction<any>;
    currentUser: MockedFunction<any>;
    authenticated: MockedFunction<any>;
    token: MockedFunction<any>;
  }

  const stubAuthRepository = (): AuthRepositoryStub => ({
    login: vi.fn(),
    logout: vi.fn(),
    currentUser: vi.fn(),
    authenticated: vi.fn(),
    token: vi.fn(),
  });

  const wrap = (authRepository: AuthRepositoryStub): VueWrapper => {
    provide(AUTH_REPOSITORY, authRepository);
    return mount(AuthVue);
  };

  const setAuthenticatedState = (authRepository: AuthRepositoryStub, authenticated: boolean) => {
    authRepository.authenticated.resolves(authenticated);
    if (authenticated) {
      const mockUser: AuthenticatedUser = {
        activated: true,
        authorities: ['ROLE_USER'],
        email: 'test-user@example.com',
        firstName: 'Test',
        lastName: 'User',
        langKey: 'en',
        login: 'test.user',
      };
      authRepository.currentUser.resolves(mockUser);
    }
  };

  it('should render login form when not authenticated', async () => {
    const authRepository = stubAuthRepository();
    setAuthenticatedState(authRepository, false);

    const wrapper = wrap(authRepository);
    await flushPromises();

    expect(wrapper.find('form').exists()).toBe(true);
    expect(wrapper.find('input[type="text"]').exists()).toBe(true);
    expect(wrapper.find('input[type="password"]').exists()).toBe(true);
    expect(wrapper.find('button').text()).toBe('Login');
  });

  it('should render welcome message and logout button when authenticated', async () => {
    const authRepository = stubAuthRepository();
    setAuthenticatedState(authRepository, true);

    const wrapper = wrap(authRepository);
    await flushPromises();

    expect(wrapper.find('form').exists()).toBe(false);
    expect(wrapper.find('p').text()).toBe('Welcome, test.user');
    expect(wrapper.find('button').text()).toBe('Logout');
  });

  it('should not call getCurrentUser when not authenticated', async () => {
    const authRepository = stubAuthRepository();
    setAuthenticatedState(authRepository, false);

    const wrapper = wrap(authRepository);
    await flushPromises();

    expect(authRepository.authenticated.called).toBe(true);
    expect(authRepository.currentUser.called).toBe(false);
    expect(wrapper.find('form').exists()).toBe(true);
  });
});
```

### 5. AxiosAuthInterceptor.spec.ts

Location: `src/test/webapp/unit/shared/http/infrastructure/secondary/AxiosAuthInterceptor.spec.ts`

This file contains unit tests for the AxiosAuthInterceptor.

```typescript
import { AUTH_REPOSITORY } from '@/auth/application/AuthProvider';
import type { AuthRepository } from '@/auth/domain/AuthRepository';
import { provide } from '@/injections';
import { setupAxiosInterceptors } from '@/shared/http/infrastructure/secondary/AxiosAuthInterceptor';
import type { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import { AxiosHeaders } from 'axios';
import type { MockedFunction } from 'vitest';
import { describe, expect, it, vi } from 'vitest';
import type { AxiosStubInstance } from './AxiosStub';
import { stubAxiosInstance } from './AxiosStub';

interface MockAuthRepository extends AuthRepository {
  login: MockedFunction<any>;
  logout: MockedFunction<any>;
  currentUser: MockedFunction<any>;
  authenticated: MockedFunction<any>;
  token: MockedFunction<any>;
}

describe('AxiosAuthInterceptor', () => {
  let axiosInstance: AxiosStubInstance;
  let mockAuthRepository: MockAuthRepository;

  const setupTest = () => {
    axiosInstance = stubAxiosInstance();
    mockAuthRepository = {
      login: vi.fn(),
      logout: vi.fn(),
      currentUser: vi.fn(),
      authenticated: vi.fn(),
      token: vi.fn(),
    };
    provide(AUTH_REPOSITORY, mockAuthRepository);
    setupAxiosInterceptors(axiosInstance);
  };

  it('should add Authorization header for authenticated requests', () => {
    setupTest();
    mockAuthRepository.authenticated.resolves(true);
    mockAuthRepository.token.resolves('fake-token');
    const config: InternalAxiosRequestConfig = { headers: new AxiosHeaders() };

    axiosInstance.runInterceptors(config).then(interceptedConfig => {
      expect(mockAuthRepository.authenticated.called).toBe(true);
      expect(mockAuthRepository.token.called).toBe(true);
      expect(interceptedConfig.headers['Authorization']).toBe('Bearer fake-token');
    });
  });

  it('should not add Authorization header for unauthenticated requests', () => {
    setupTest();
    mockAuthRepository.authenticated.resolves(false);
    const config: InternalAxiosRequestConfig = { headers: new AxiosHeaders() };

    axiosInstance.runInterceptors(config).then(interceptedConfig => {
      expect(mockAuthRepository.authenticated.called).toBe(true);
      expect(mockAuthRepository.token.called).toBe(false);
      expect(interceptedConfig.headers['Authorization']).toBeUndefined();
    });
  });

  it('should call logout on 401 response', () => {
    setupTest();
    const error: AxiosError = {
      response: { status: 401 } as AxiosResponse,
      isAxiosError: true,
      toJSON: () => ({}),
      name: '',
      message: '',
    };
    const responseInterceptor = axiosInstance.interceptors.response.use.args[0][1];

    responseInterceptor(error).catch(() => {
      expect(mockAuthRepository.logout.called).toBe(true);
    });
  });

  it('should not call logout for non-401 errors', () => {
    setupTest();
    const error: AxiosError = {
      response: { status: 500 } as AxiosResponse,
      isAxiosError: true,
      toJSON: () => ({}),
      name: '',
      message: '',
    };
    const responseInterceptor = axiosInstance.interceptors.response.use.args[0][1];

    responseInterceptor(error).catch(() => {
      expect(mockAuthRepository.logout.called).toBe(false);
    });
  });

  it('should pass through successful responses without modification', () => {
    setupTest();
    const mockResponse: AxiosResponse = {
      data: {},
      status: 200,
      statusText: 'OK',
      headers: {},
      config: {} as InternalAxiosRequestConfig,
    };
    const responseInterceptor = axiosInstance.interceptors.response.use.args[0][0];

    const result = responseInterceptor(mockResponse);
    expect(result).toEqual(mockResponse);
  });
});
```

### 6. AxiosStub.ts

Location: `src/test/webapp/unit/shared/http/infrastructure/secondary/AxiosStub.ts`

This file provides a stub for Axios to be used in tests.

```typescript
import type { AxiosInstance, AxiosInterceptorManager, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import type { MockedFunction } from 'vitest';
import { vi } from 'vitest';

export interface AxiosStubInterceptorManager extends AxiosInterceptorManager<InternalAxiosRequestConfig> {
  use: MockedFunction<any>;
  eject: MockedFunction<any>;
  clear: MockedFunction<any>;
}

export interface AxiosStubInstance extends AxiosInstance {
  get: MockedFunction<any>;
  put: MockedFunction<any>;
  post: MockedFunction<any>;
  delete: MockedFunction<any>;
  interceptors: {
    request: AxiosStubInterceptorManager;
    response: AxiosStubInterceptorManager;
  };
  runInterceptors: (config: InternalAxiosRequestConfig) => Promise<InternalAxiosRequestConfig>;
}

export const stubAxiosInstance = (): AxiosStubInstance => {
  const instance = {
    get: vi.fn(),
    put: vi.fn(),
    post: vi.fn(),
    delete: vi.fn(),
    interceptors: {
      request: {
        use: vi.fn(),
        eject: vi.fn(),
        clear: vi.fn(),
      },
      response: {
        use: vi.fn(),
        eject: vi.fn(),
        clear: vi.fn(),
      },
    },
    runInterceptors: async (config: InternalAxiosRequestConfig) => {
      let currentConfig = { ...config, headers: config.headers || {} };
      for (const interceptor of instance.interceptors.request.use.args) {
        currentConfig = await interceptor[0](currentConfig);
      }
      return currentConfig;
    },
  } as AxiosStubInstance;
  return instance;
};

export const dataAxiosResponse = <T>(data: T): AxiosResponse<T> =>
  ({
    data,
  }) as AxiosResponse<T>;
```

### 7. router.ts

Location: `src/main/webapp/app/router.ts`

This file sets up the main router for the application, including authentication routes.

```typescript
import { authRoutes } from '@/auth/application/AuthRouter';
import { homeRoutes } from '@/home/application/HomeRouter';
import { createRouter, createWebHistory } from 'vue-router';

export const routes = [...homeRoutes(), ...authRoutes()];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
```

### 8. main.ts

Location: `src/main/webapp/app/main.ts`

This file sets up the main application by configuring the Axios interceptors.

```typescript
// (ignoring previous code) ...
setupAxiosInterceptors(axiosInstance);
// jhipster-needle-main-ts-provider
app.mount('#app');
```

## JHLite Backend

Start the JHLite application and apply the `spring-boot-jwt-basic-auth` module and the required dependencies.

Then edit the `application-local.yml` file and add the following configuration:

```yaml
application:
  cors:
    allowed-origins:
      - http://localhost:8100
      - http://localhost:9000
    allowed-methods: '*'
    allowed-headers: '*'
    exposed-headers:
      - Authorization
      - Link
      - X-Total-Count
      - X-jhipster-alert
      - X-jhipster-error
      - X-jhipster-params
    allow-credentials: true
    max-age: 1800
    allowed-origin-patterns:
      - https://*.githubpreview.dev
```

Then run the application using the local profile (the following example uses the Maven wrapper):

```bash
./mvnw -Dspring-boot.run.profiles=local
```

Now you can log in using the following credentials:

- Username: `admin`
- Password: `admin`

## Conclusion

This documentation provides a detailed overview of the JWT authentication components and utilities within our Vue.js application. It covers both the primary application files related to routing and authentication, as well as the test files used to validate the functionality of these components.

The core of the authentication system is the `AuthVue` component, which manages user login and logout processes. The `AxiosAuthInterceptor` ensures that all authenticated requests are properly equipped with the necessary JWT authorization headers.

The accompanying test files (`AuthVue.spec.ts`, `AxiosAuthInterceptor.spec.ts`) demonstrate how to effectively test these components, while the stub file (`AxiosStub.ts`) provides mock implementations of Axios, enabling more streamlined and reliable testing.

For the backend setup, the **JHLite Backend** documentation outlines the necessary steps to configure the `spring-boot-jwt-basic-auth` module, which serves as the foundation for handling authentication and authorization in the application. This integration ensures a secure and robust communication layer between the frontend and backend, enabling JWT-based authentication.
