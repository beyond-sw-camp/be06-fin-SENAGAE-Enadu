module.exports = {

    testMatch: ['**/*.spec.[jt]s?(x)', '**/*.test.[jt]s?(x)'],
    transform: {
        '^.+\\.vue$': 'vue-jest',
        '^.+\\.js$': 'babel-jest',
    },
    moduleNameMapper: {
        '^@/(.*)$': '<rootDir>/src/$1',
    },
    moduleFileExtensions: ['js', 'json', 'vue']
};