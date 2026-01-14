import zod from "zod";
export const schema = zod.object({
  url: zod
    .string()
    .trim()
    .nonempty("Informe uma URL.")
    .superRefine((val, ctx) => {
      const messages: string[] = [];

      // espaços em branco não são permitidos
      if (/\s/.test(val)) {
        messages.push("A URL contém espaços.");
      }

      // tentar construir URL absoluta
      let parsed: URL | null = null;
      try {
        parsed = new URL(val);
      } catch (err) {
        messages.push("Formato inválido de URL (ex.: https://exemplo.com).");
      }

      if (parsed) {
        // protocolo -> permitir apenas http(s)
        const proto = parsed.protocol; // inclui ':', ex 'https:'
        if (!(proto === "http:" || proto === "https:")) {
          messages.push("Protocolo inválido: use http ou https.");
        }

        // não permitir credenciais na URL (username:password@)
        if (parsed.username || parsed.password) {
          messages.push(
            "A URL não deve conter credenciais (username:password@)."
          );
        }

        // hostname válido: deve ter pelo menos um ponto e TLD com 2+ letras
        const host = parsed.hostname; // sem porta
        // rejeita pontos consecutivos e host vazio
        if (!host || host.includes("..")) {
          messages.push("Hostname inválido.");
        } else {
          // domínio razoável: algo.sufixo (sufixo 2+ letras) ou IP
          const domainRegex = /^[a-z0-9.-]+\.[a-z]{2,}$/i;
          const ipV4Regex = /^(?:\d{1,3}\.){3}\d{1,3}$/;
          if (!(domainRegex.test(host) || ipV4Regex.test(host))) {
            messages.push(
              "Hostname inválido (ex.: exemplo.com) ou IP inválido."
            );
          } else if (ipV4Regex.test(host)) {
            // checar cada octeto 0-255
            const octets = host.split(".").map(Number);
            if (octets.some((n) => Number.isNaN(n) || n < 0 || n > 255)) {
              messages.push("IP inválido no hostname.");
            }
          }
        }

        // porta (se presente) tem que ser número entre 1 e 65535
        if (parsed.port) {
          const p = Number(parsed.port);
          if (!Number.isInteger(p) || p < 1 || p > 65535) {
            messages.push("Porta inválida (deve ser 1-65535).");
          }
        }

        // regra adicional opcional: não aceitar URLs que terminem com '.' no hostname
        if (host && host.endsWith(".")) {
          messages.push("Hostname não deve terminar com ponto.");
        }
      }

      // se coletamos mensagens, adicionar uma única issue com todas (Zod -> react-hook-form mostrará a mensagem)
      if (messages.length > 0) {
        ctx.addIssue({
          code: zod.ZodIssueCode.custom,
          message: messages.join(" "),
        });
      }
    }),
});
