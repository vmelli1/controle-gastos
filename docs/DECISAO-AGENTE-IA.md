# Decisão de Projeto: Agente de IA no Controle de Gastos

**Status:** adiado. Não implementar agora; adotar alternativa por regra de negócio.

---

## Nível Estratégico

### Objetivo

Implementar um agente de IA dentro do Controle de Gastos.

### Por que implementar uma IA em um controle de gastos?

- Acompanhar os gastos do usuário conforme ele os adiciona ao longo do mês e dos meses seguintes.
- Identificar em qual categoria o usuário está gastando mais.
- Aconselhar o corte de alguns gastos, para que o usuário economize mais de acordo com o próprio histórico.

### O que isso agregaria ao sistema e ao negócio

- Atrair novos usuários a cada dia.

### Restrições

- No momento, e nos próximos meses (possivelmente ao longo de um ano), não há verba para custear o uso de agentes de IA e o consumo de tokens diariamente. O custo depende de quantos usuários ativos utilizam o sistema.
- Seria possível oferecer planos pagos para o uso do agente de IA, mas este não é o momento ideal para implementá-lo.

### Decisão

Não implementar o agente de IA agora.

---

## Nível Tático

### O que fazer para implementar o agente de IA

Existem dois caminhos:

1. **Economizar e esperar o momento ideal** para implementar o agente de IA.
2. **Encontrar uma forma mais eficaz usando regras de negócio dentro do próprio sistema.**

### Alternativa por regra de negócio

Caso o usuário defina uma meta, o próprio sistema, ao identificar que o usuário ultrapassou o valor da meta, orienta o usuário a economizar mais naquela categoria.

---

## Nível Operacional

Não definido no momento. Como os níveis anteriores ainda não têm um caminho fechado, o operacional não entra nesta etapa.

---

## Resumo

| Nível | Pergunta | Resposta |
|---|---|---|
| Estratégico | Por que e para quê? | Acompanhar e orientar o usuário nos gastos e atrair novos usuários. Sem verba agora, então adiar. |
| Tático | Como chegar lá? | Esperar o momento ideal ou usar regra de negócio (alerta quando a meta for ultrapassada). |
| Operacional | Quem faz o quê? | A definir. |
