package com.drizzlepal.crypto.asymmetric;

import java.security.spec.ECGenParameterSpec;

public enum ECGenParameters {

    /**
     * SM2 曲线（SM2P256V1）
     * - 描述：这是中国国家标准的椭圆曲线，基于 256 位的曲线。
     * - 优点：符合中国国家密码标准，适用于需要遵循 SM2 标准的应用场景。
     * - 缺点：在国际上较少使用，可能不被所有系统兼容。
     * - 作用：广泛用于中国政府和企业的安全应用。
     */
    sm2p256v1,

    /**
     * SECP256R1 曲线（P-256）
     * - 描述：这是 NIST 标准的椭圆曲线之一，基于 256 位的曲线。
     * - 优点：广泛使用，提供良好的安全性和性能。
     * - 缺点：安全性高，但密钥长度比更长曲线要短。
     * - 作用：广泛应用于 TLS/SSL、数字签名等。
     */
    secp256r1,

    /**
     * SECP384R1 曲线（P-384）
     * - 描述：这是 NIST 标准的椭圆曲线之一，基于 384 位的曲线。
     * - 优点：比 SECP256R1 提供更高的安全性。
     * - 缺点：密钥长度和计算开销较大。
     * - 作用：适用于对安全性要求更高的场景。
     */
    secp384r1,

    /**
     * SECP521R1 曲线（P-521）
     * - 描述：这是 NIST 标准的椭圆曲线之一，基于 521 位的曲线。
     * - 优点：提供最高的安全性，适合长期保密的数据。
     * - 缺点：计算开销最大，密钥长度较长。
     * - 作用：用于需要极高安全性的场景，如长期存储敏感数据。
     */
    secp521r1,

    /**
     * SECP192R1 曲线（P-192）
     * - 描述：早期使用的椭圆曲线，基于 192 位的曲线。
     * - 优点：计算开销较小，性能较好。
     * - 缺点：安全性较低，已不再推荐用于新的系统。
     * - 作用：不推荐用于现代应用，仅在遗留系统中使用。
     */
    secp192r1,

    /**
     * SECP224R1 曲线（P-224）
     * - 描述：比 SECP192R1 提供更高的安全性，基于 224 位的曲线。
     * - 优点：比 SECP192R1 提供更高的安全性，计算开销适中。
     * - 缺点：安全性和密钥长度比 SECP256R1 要低。
     * - 作用：适用于对安全性要求不高但需要性能的场景。
     */
    secp224r1,

    /**
     * BrainpoolP256r1 曲线
     * - 描述：基于 256 位的 Brainpool 曲线，提供替代 NIST 标准的曲线。
     * - 优点：设计独立于 NIST，适用于需要独立标准的应用。
     * - 缺点：使用不如 NIST 曲线广泛，兼容性可能有问题。
     * - 作用：用于需要遵循 Brainpool 标准的安全应用。
     */
    brainpoolP256r1,

    /**
     * BrainpoolP384r1 曲线
     * - 描述：基于 384 位的 Brainpool 曲线，提供更高的安全性。
     * - 优点：比 BrainpoolP256r1 提供更高的安全性。
     * - 缺点：计算开销较大，使用不如 NIST 曲线广泛。
     * - 作用：适用于对安全性要求更高的应用。
     */
    brainpoolP384r1,

    /**
     * BrainpoolP512r1 曲线
     * - 描述：基于 512 位的 Brainpool 曲线，提供最高的安全性。
     * - 优点：提供极高的安全性，适合长期保密的数据。
     * - 缺点：计算开销最大，使用较少。
     * - 作用：用于需要最高安全性的场景。
     */
    brainpoolP512r1;

    protected ECGenParameterSpec ECGenParameter;

    ECGenParameters() {
        this.ECGenParameter = new ECGenParameterSpec(this.name());
    }

    public ECGenParameterSpec getECGenParameter() {
        return this.ECGenParameter;
    }

}
